package com.yuly.elaundry.kurir.model.peta;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.util.PointList;
import com.graphhopper.util.StopWatch;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.peta.CariRuteActivity;
import com.yuly.elaundry.kurir.controller.fragment.DialogDownload;

import com.yuly.elaundry.kurir.model.database.RouteDbHelper;
import com.yuly.elaundry.kurir.model.listeners.CariPetaHandlerListener;

import com.yuly.elaundry.kurir.model.util.Variable;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Cap;
import org.mapsforge.core.graphics.Join;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapDataStore;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.List;


public class CariRuteHandler {
    private AppCompatActivity activity;
    private MapView mapView;
    private String currentArea;
    private TileCache tileCache;
    private GraphHopper hopper;
    private TileRendererLayer tileRendererLayer;
    private File mapsFolder;
    private volatile boolean shortestPathRunning;
    private Marker startMarker, endMarker;
    private Polyline polylinePath, polylineTrack;
    private CariPetaHandlerListener cariPetaHandlerListener;
    private static CariRuteHandler cariRuteHandler;

    /**
     * if user going to point on map to gain a location
     */
    private boolean needLocation;
    /**
     * need to know if path calculating status change; this will trigger MapActions function
     */
    private boolean needPathCal;

    public static CariRuteHandler getCariRuteHandler() {
        if (cariRuteHandler == null) {
            reset();
        }
        return cariRuteHandler;
    }

    /**
     * reset class, build a new instance
     */
    public static void reset() {
        cariRuteHandler = new CariRuteHandler();
    }

    private CariRuteHandler() {
        setShortestPathRunning(false);
        startMarker = null;
        endMarker = null;
        polylinePath = null;
        needLocation = false;
        needPathCal = false;
    }

    public void init(AppCompatActivity activity, MapView mapView, String currentArea, File mapsFolder) {
        this.activity = activity;
        this.mapView = mapView;
        this.currentArea = currentArea;
        this.mapsFolder = mapsFolder;
        //        this.prepareInProgress = prepareInProgress;
        tileCache = AndroidUtil
                .createTileCache(activity, getClass().getSimpleName(), mapView.getModel().displayModel.getTileSize(),
                        1f, mapView.getModel().frameBufferModel.getOverdrawFactor());
    }

    /**
     * load map to mapView
     *
     * @param areaFolder
     */
    public void loadMap(File areaFolder) {
        logToast(activity.getString(R.string.memuat_peta) + currentArea);
       // File mapFile = new File(areaFolder, currentArea + activity.getString(R.string.dotmap));
     //   MapDataStore mapDataStore = new MapFile(new File(areaFolder,"indonesia_jawatimur_kediringanjuk.map"));

        try {


        MapDataStore mapDataStore = new MapFile(new File(areaFolder,currentArea +".map"));

        mapView.getLayerManager().getLayers().clear();
        tileRendererLayer =
              //  new TileRendererLayer(tileCache, mapView.getModel().mapViewPosition, false, true,
                //        AndroidGraphicFactory.INSTANCE)

         new TileRendererLayer(tileCache, mapDataStore,
                mapView.getModel().mapViewPosition, false, true, AndroidGraphicFactory.INSTANCE)

                {
                  /*  @Override public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
                        return myOnTap(tapLatLong, layerXY, tapXY);
                    }*/
                };

       // tileRendererLayer.setMapFile(mapDataStore);
        tileRendererLayer.setTextScale(0.8f);
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
                 log("last location " + Variable.getVariable().getLastLocation());
        if (Variable.getVariable().getLastLocation() == null) {
         //   centerPointOnMap(tileRendererLayer.getMapDatabase().getMapFileInfo().boundingBox.getCenterPoint(), 6);

            mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(mapDataStore.boundingBox().getCenterPoint(), (byte) 15));

        } else {
            centerPointOnMap(Variable.getVariable().getLastLocation(), Variable.getVariable().getLastZoomLevel());
        }

        mapView.getLayerManager().getLayers().add(tileRendererLayer);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.addContentView(mapView, params);
        loadGraphStorage();

        } catch (Exception e){
            Log.e("error", String.valueOf(e));
           // Toast.makeText(activity,String.valueOf(e),Toast.LENGTH_LONG).show();

            CariRuteActivity panggilDialog = new CariRuteActivity();
            //panggilDialog.panggilDialogDownload();

             //HelpUtils.showAbout(activity);

            DialogDownload.showDownloadUlangDialog(activity);

        };

    }


    /**
     * center the LatLong point in the map and zoom map to zoomLevel
     *
     * @param latLong
     * @param zoomLevel (if 0 use current zoomlevel)
     */
    public void centerPointOnMap(LatLong latLong, int zoomLevel) {
        if (zoomLevel == 0) {
            mapView.getModel().mapViewPosition
                    .setMapPosition(new MapPosition(latLong, mapView.getModel().mapViewPosition.getZoomLevel()));
        } else {mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(latLong, (byte) zoomLevel));}
    }




    // TODO: 04/12/16 menambahkan marker merah
    public void tambahMarkerMerah(LatLong point) {
        Layers layers = mapView.getLayerManager().getLayers();
        //        if (startPoint != null && endPoint != null) {
        //            setShortestPathRunning(true);
        //        }
        if (point != null) {
          //  removeLayer(layers, startMarker);
            startMarker = createMarker(point, R.drawable.ic_place_red_24dp);
            layers.add(startMarker);
        }
    }


    /**
     * remove a layer from map layers
     *
     * @param layers
     * @param layer
     */
    public void removeLayer(Layers layers, Layer layer) {
        if (layers != null && layer != null && layers.contains(layer)) {
            layers.remove(layer);
        }
    }

    /**
     * create a marker for map
     *
     * @param p
     * @param resource
     * @return
     */
    public Marker createMarker(LatLong p, int resource) {
        Drawable drawable = activity.getResources().getDrawable(resource);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        return new Marker(p, bitmap, 0, -bitmap.getHeight() / 2);
    }


    /**
     * load graph from storage: Use and ready to search the map
     */
    private void loadGraphStorage() {
        new AsyncTask<Void, Void, Path>() {
            String error = "";

            protected Path doInBackground(Void... v) {
                try {
                    GraphHopper tmpHopp = new GraphHopper().forMobile();
                    tmpHopp.setCHWeighting(Variable.getVariable().getWeighting());
                    tmpHopp.load(new File(mapsFolder, currentArea).getAbsolutePath());
                    hopper = tmpHopp;
                } catch (Exception e) {
                    error = activity.getString(R.string.error_titikdua) + e.getMessage();
                }
                return null;
            }

            protected void onPostExecute(Path o) {
                if (error != "") {
                    logToast(activity.getString(R.string.error_grafik) +" : "+ error +" mapfolder : "+ mapsFolder +" current area : "+ currentArea +" hopper : "+hopper);
                } else {
                }
                Variable.getVariable().setPrepareInProgress(false);
            }
        }.execute();
    }


    public String menghitungJarak(double fromLat, double fromLon, double toLat, double toLon){

        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon);
        req.setAlgorithm(Variable.getVariable().getRoutingAlgorithms());
        req.getHints().put(activity.getString(R.string.instruksi),
                //  Variable.getVariable().getDirectionsON()
                false

        );
        req.setVehicle(Variable.getVariable().getTravelMode());
        //req.setVehicle("car");
        req.setWeighting(Variable.getVariable().getWeighting());
        //req.setWeighting("fastest");
        GHResponse resp = hopper.route(req);

        return String.valueOf(Math.round(resp.getDistance()/1f));
    }


    /**
     * calculate a path: start to end
     *
     * @param fromLat
     * @param fromLon
     * @param toLat
     * @param toLon
     */
    public void calcPath(final double fromLat, final double fromLon, final double toLat, final double toLon) {
        Layers layers = mapView.getLayerManager().getLayers();
        removeLayer(layers, polylinePath);
        polylinePath = null;
        new AsyncTask<Void, Void, GHResponse>() {
            float time;

            protected GHResponse doInBackground(Void... v) {
                StopWatch sw = new StopWatch().start();
                GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon);
                req.setAlgorithm(Variable.getVariable().getRoutingAlgorithms());
                req.getHints().put(activity.getString(R.string.instruksi),
                      //  Variable.getVariable().getDirectionsON()
                        false

                );
              req.setVehicle(Variable.getVariable().getTravelMode());
                //req.setVehicle("car");
              req.setWeighting(Variable.getVariable().getWeighting());
                //req.setWeighting("fastest");
                GHResponse resp = hopper.route(req);
                time = sw.stop().getSeconds();
                return resp;
            }

            protected void onPreExecute() {
                super.onPreExecute();
                setShortestPathRunning(true);
            }

            protected void onPostExecute(GHResponse resp) {
                if (!resp.hasErrors()) {
                    polylinePath = createPolyline(resp.getPoints(),
                            activity.getResources().getColor((R.color.my_primary_dark_transparent)), 10);



                    mapView.getLayerManager().getLayers().add(polylinePath);

/*
                    if (Variable.getVariable().isDirectionsON()) {
                        DetailPetaNavigasi.getNavigator().setGhResponse(resp);
                        //                        log("navigator: " + Navigator.getNavigator().toString());
                    }
                    */

                    log("from:" + fromLat + "," + fromLon + " to:" + toLat + ","
                            + toLon + " found path with distance:" + resp.getDistance()
                            / 1000f + ", nodes:" + resp.getPoints().getSize() + ", time:"
                            + time + " " + resp.getDebugInfo());

                   // Log.d("respone get point ", String.valueOf(resp.getPoints()));

/*
                    logUser("rute sejauh " + (int) (resp.getDistance() / 100) / 10f
                            + "km, waktu tempuh :" + resp.getTime() / 60000f + " menit, lama perhitungan :" + time);
*/

                } else {
                    logToast(activity.getString(R.string.error_titikdua) + resp.getErrors());
                }
                try {
                    activity.findViewById(R.id.map_nav_settings_path_finding).setVisibility(View.GONE);
                    activity.findViewById(R.id.nav_settings_layout).setVisibility(View.VISIBLE);
                } catch (Exception e) {e.getStackTrace();}
                setShortestPathRunning(false);

            }
        }.execute();
    }
    /**
     * @return true if already loaded
     */
    boolean isReady() {
        if (hopper != null) return true;
        if (Variable.getVariable().isPrepareInProgress()) {
            //   "Preparation still in progress";
            return false;
        }
        //        "Prepare finished but hopper not ready. This happens when there was an error while loading the files";
        return false;
    }

    private PointList trackingPointList;


    /**
     * draws a connected series of line segments specified by a list of LatLongs.
     *
     * @param pointList
     * @param color:       the color of the polyline
     * @param strokeWidth: the stroke width of the polyline
     * @return Polyline
     */
    public Polyline createPolyline(PointList pointList, int color, int strokeWidth) {
        Paint paintStroke = AndroidGraphicFactory.INSTANCE.createPaint();

        paintStroke.setStyle(Style.STROKE);
        paintStroke.setStrokeJoin(Join.ROUND);
        paintStroke.setStrokeCap(Cap.ROUND);
        paintStroke.setColor(color);
                paintStroke.setDashPathEffect(new float[]{25, 8});
        paintStroke.setStrokeWidth(strokeWidth);

        // TODO: new mapsforge version wants an mapsforge-paint, not an android paint.
        // This doesn't seem to support transparceny
        //paintStroke.setAlpha(128);
        Polyline line = new Polyline((Paint) paintStroke, AndroidGraphicFactory.INSTANCE);
        List<LatLong> geoPoints = line.getLatLongs();
        // SqLite database handler
        RouteDbHelper db_rute = new RouteDbHelper(this.activity);

        PointList tmp = pointList;
        for (int i = 0; i < pointList.getSize(); i++) {
            geoPoints.add(new LatLong(tmp.getLatitude(i), tmp.getLongitude(i)));

/*            //
            Log.d(String.valueOf(tmp.getLatitude(i)), String.valueOf(tmp.getLongitude(i)));

            Lokasi lokasi_konsumen = new Lokasi( "kon id","pemesanan id", String.valueOf(tmp.getLatitude(i)), String.valueOf(tmp.getLongitude(i)), "0",1);

            long id = db_rute.createPointKonsumen(lokasi_konsumen);

            Log.d("ID Point", String.valueOf(id));*/

        }
        return line;
    }

    public boolean isShortestPathRunning() {
        return shortestPathRunning;
    }

    private void setShortestPathRunning(boolean shortestPathRunning) {
        this.shortestPathRunning = shortestPathRunning;
        if (cariPetaHandlerListener != null && needPathCal) cariPetaHandlerListener.pathCalculating(shortestPathRunning);
    }


    /**
     * only tell on object
     *
     * @param rutePetaHandlerListener
     */
    public void setRutePetaHandlerListener(CariPetaHandlerListener rutePetaHandlerListener) {
        this.cariPetaHandlerListener = rutePetaHandlerListener;
    }



    public Activity getActivity() {
        return activity;
    }

    /**
     * send message to logcat
     *
     * @param str
     */
    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-----------------" + str);
    }


    /**
     * send message to logcat and Toast it on screen
     *
     * @param str: message
     */
    private void logToast(String str) {
        log(str);
        Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
    }


    private void logUser( String str )
    {
        log(str);
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }
}
