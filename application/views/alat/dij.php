<?php

  //Função de callback para extrair os vertices que possuem ligação com o vertice anterior e o seu peso
  function peso( $var ) {
    return( $var !== "0" ); //retorna o que for diferente de zero
  }

  // Função de calculo do menor caminho para os demais vertices
  function shortpath ( $initial, $array , $csv) {

    // Percorre o array que foi recebido
    foreach ( $array as $key => $value ){

      // Seta a distância entre os vertices, já setando a distancia zero para o mesmo vertice
      if ( $key == $initial ) {
        $priorities[ $key ] = 0;
        $distance [ $key ] = 0;
      }else{
        $priorities[ $key ] = INF;
        $distance [ $key ] = INF;
      }
    }

    while ( count( $priorities ) > 0 ) {
      // Seta o menor valor da lista de prioridades como próximo vertice inicial
      $initial = array_search( min( $priorities ), $priorities );
      unset( $priorities [ $initial ] ); // Retira o menor valor da lista de prioridades

      // Descobre os vertices que possuem ligação com o vertice atual
      $next = array_filter( $csv[ $initial ], "peso" );

      // Valida se é um menor caminho e insere o mesmo
      foreach ( $next as $nxkey => $nxvalue ) {
        if ( $distance[ $nxkey ] > ( $distance [ $initial ] + $nxvalue ) ) {
          $distance[ $nxkey ] = ( $distance [ $initial ] + $nxvalue );
          $priorities[ $nxkey ] = ( $distance [ $initial ] + $nxvalue );
        }
      }
    }
    // Retorna o array com as distâncias
    return $distance;
  }

  // Função a qual encontra os menores caminhos e os retorna
  function dijkstra( $in ){

    // Copia a estrutura da matriz de entrada gerando assim a matriz de saída
    $out = $in;

    // Executa o algoritmo de djikstra em cada elemento do grafo (cada linha da matriz)
    foreach ( $out as $ver => $dest ){
      //Calcula o menor caminho de um vertice inicial
      $out[$ver] = shortpath ( $ver, $out[$ver], $in );
    }

    //Retorna o csv final para impressão
    return $out;
  }
?>
