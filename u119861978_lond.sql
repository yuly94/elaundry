
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 20, 2016 at 01:37 PM
-- Server version: 10.0.20-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u119861978_lond`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(30) NOT NULL,
  `pass` varchar(70) NOT NULL,
  `foto` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `uname`, `pass`, `foto`) VALUES
(8, 'malasngoding', '069c546d1d97fd9648d8142b3e0fd3a3', 'text.png');

-- --------------------------------------------------------

--
-- Table structure for table `alamat`
--

CREATE TABLE IF NOT EXISTS `alamat` (
  `tempat_id` int(11) NOT NULL AUTO_INCREMENT,
  `konsumen_id` varchar(50) DEFAULT NULL,
  `tempat_nama` varchar(25) NOT NULL,
  `tempat_alamat` varchar(100) NOT NULL,
  `tempat_kota` varchar(20) NOT NULL,
  `tempat_provinsi` varchar(20) NOT NULL,
  `tempat_latitude` varchar(100) NOT NULL,
  `tempat_longitude` varchar(200) NOT NULL,
  `tempat_created` datetime NOT NULL,
  `tempat_updated` datetime NOT NULL,
  `tempat_status` int(2) NOT NULL,
  `tempat_tipe` varchar(10) NOT NULL DEFAULT 'laundry',
  PRIMARY KEY (`tempat_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `alamat`
--

INSERT INTO `alamat` (`tempat_id`, `konsumen_id`, `tempat_nama`, `tempat_alamat`, `tempat_kota`, `tempat_provinsi`, `tempat_latitude`, `tempat_longitude`, `tempat_created`, `tempat_updated`, `tempat_status`, `tempat_tipe`) VALUES
(1, 'a1353a67c290b2287b947a130379f3be', 'Rumah', 'Dsn. Jabon Rt 02/08, Desa Drenges', 'Kertosono', 'Nganjuk', '-7.636653', '112.081710', '2016-02-10 00:00:00', '0000-00-00 00:00:00', 1, 'laundry');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` text NOT NULL,
  `jenis` text NOT NULL,
  `suplier` text NOT NULL,
  `modal` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sisa` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id`, `nama`, `jenis`, `suplier`, `modal`, `harga`, `jumlah`, `sisa`) VALUES
(14, 'roti unibis', 'makanan', 'pt.makamur jaya', 5000, 6500, 350, 20),
(17, 'tim tam', 'makanan ringan', 'pt surga', 2000, 6000, 792, 10),
(19, 'tic tac', 'makanan ringan', 'pt sido multp', 2000, 4000, 2, 24),
(20, 'aqua sedang', 'minuman ringan', 'pt jamaika', 1000, 3000, 990, 1000),
(21, 'makkkanan', 'makanan ringan', 'pt surya', 2000, 4000, 894, 900),
(22, 'makkkanan', 'makanan ringan', 'pt surya', 2000, 4000, 894, 900),
(23, 'magnum', 'rokok', 'pt surga', 12000, 13000, 997, 1000),
(24, 'santri mie', 'makanan ringan', 'pt sido mulyo', 2000, 4000, 784, 800),
(25, 'rambut palsu', 'accesories', 'pt ptan', 3000, 5000, 496, 500),
(26, 'rambut palsu', 'accesories', 'pt ptan', 3000, 5000, 496, 500),
(27, 'sea foog', 'makanan ringan', 'pt surga', 4000, 60000, 598, 600),
(28, 'mild', 'rokok', 'pt sampoerna', 15000, 17000, 192, 200),
(29, 'dji sam soe', 'rokok', 'pt ptan', 14000, 15000, 145, 150),
(30, 'nu mild', 'rokok', 'pt ptan', 14000, 15000, 144, 150),
(31, 'nu mild', 'rokok', 'pt ptan', 14000, 15000, 144, 150),
(32, 'roti', 'makanan', 'hdhnhnhh', 2000, 5000, 4, 6);

-- --------------------------------------------------------

--
-- Table structure for table `barang_laku`
--

CREATE TABLE IF NOT EXISTS `barang_laku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` date NOT NULL,
  `nama` text NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `total_harga` int(20) NOT NULL,
  `laba` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=64 ;

--
-- Dumping data for table `barang_laku`
--

INSERT INTO `barang_laku` (`id`, `tanggal`, `nama`, `jumlah`, `harga`, `total_harga`, `laba`) VALUES
(46, '2015-02-01', 'roti unibis', 2, 6000, 12000, 2000),
(47, '2015-02-02', 'makkkanan', 7, 12000, 84000, 70000),
(48, '2015-02-02', 'dji sam soe', 2, 15000, 30000, 2000),
(49, '2015-02-03', 'makkkanan', 1, 12000, 12000, 10000),
(50, '2015-02-01', 'tim tam', 2, 4000, 8000, 4000),
(51, '2015-02-02', 'mild', 2, 17000, 34000, 4000),
(52, '2015-02-03', 'magnum', 1, 18000, 18000, 6000),
(53, '2015-02-06', 'dji sam soe', 2, 19000, 38000, 10000),
(54, '2015-02-15', 'nu mild', 2, 19100, 38200, 10200),
(55, '2015-02-27', 'roti unibis', 2, 8000, 16000, 6000),
(56, '2015-02-19', 'roti unibis', 1, 7000, 7000, 2000),
(57, '2015-01-14', 'roti unibis', 1, 7000, 7000, 2000),
(58, '2015-02-01', 'pulpen', 1, 3000, 3000, 2000),
(59, '2015-02-02', 'roti', 2, 3000, 6000, 2000),
(63, '2016-01-22', 'tic tac', 8, 4000, 32000, 16000);

-- --------------------------------------------------------

--
-- Table structure for table `daftar_pembayaran`
--

CREATE TABLE IF NOT EXISTS `daftar_pembayaran` (
  `daftar_pembayaran_id` int(11) NOT NULL,
  `nilai_pembayaran` varchar(20) NOT NULL,
  `pembayar_id` varchar(20) NOT NULL,
  `penerima_id` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `konsumen`
--

CREATE TABLE IF NOT EXISTS `konsumen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `konsumen_id` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `nohp` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password_hash` varchar(100) NOT NULL,
  `api_key` varchar(100) NOT NULL,
  `activation` varchar(100) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `actived` tinyint(1) NOT NULL DEFAULT '0',
  `resetToken` varchar(100) NOT NULL,
  `resetComplete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `konsumen`
--

INSERT INTO `konsumen` (`id`, `konsumen_id`, `nama`, `alamat`, `nohp`, `email`, `password_hash`, `api_key`, `activation`, `status`, `created_at`, `last_login`, `updated_at`, `actived`, `resetToken`, `resetComplete`) VALUES
(1, 'a1353a67c290b2287b947a130379f3be', 'yuly nurhidayati', '085771723577', '085735971354', 'yulynur2013@gmail.com', '$2a$10$f7a6603015b941bd543d5e22LwbtSjKxjP2AI8Pdgdl88rR7f.gIW', '32722747c07e952202437bacb924906a78e06043', '', 1, '2016-02-23 07:41:35', '2016-07-18 07:30:03', '2016-07-18 10:42:02', 1, '', 0),
(12, '5b86f467c6904b0f3e578cc72983edf2', 'yuly nurhidayati', 'jabon', '0812345', 'abdurrochmanwachid@gmail.com', '$2a$10$b0853dd5bf6b588f6242aOv7Whiz1DjJ37SD6L/xsenLRHaf3MFIC', 'bbdb63d523c9fe281a9aed1c564bc00afc7a8453', '1b5f249d3581304dd764a42a63650024', 1, '2016-08-02 05:59:03', NULL, NULL, 0, '', 0),
(9, '6422e19a9426eaebc9d678d84cfe93d8', 'Yudha ISH', 'Ujung', '0812345', 'yudha.ish@gmail.com', '$2a$10$a27de9a0150be6d7dcec2O9hgMPIZV/B9D8dhbbq5lL2ZUqcnzIjq', '832eed6f666feb76ea3a7c4dc6e40590fdf05d08', '0c38f19fbfb07d16d0d2df88e57e0bbf', 1, '2016-07-12 05:30:18', '2016-07-12 05:30:31', '2016-07-17 23:13:10', 0, '', 0),
(13, 'e6f02a7f63de033a33d72db60b180e45', 'yuly nurhidayati', 'jabon', '0812345', 'wachid.sst@gmail.com', '$2a$10$3b222ee2bf168224bd307uLQZpTPfoXQPAGLgHa.IAWVdge.q67wm', '6b93531ca0362d019f3a4bf369abc12d6685aa08', 'eb5ba83ce06e017e7dde710835aecb15', 1, '2016-08-02 06:00:19', '2016-10-19 14:53:16', NULL, 1, '', 0),
(14, 'b1892ba8d0ef614446b54d208eeb8882', 'mas', 'iya', '085', 'kemenhub.ts@gmail.com', '$2a$10$1c1aaecb0bdf031f0d6f0evOOWmMhSH8n9ExYKbU9VRBp8dALU/s2', '945fafb7ee9075d97d2a82bdcd32ab14c118ebe1', 'ad464877e05557dfe9317d327350f552', 1, '2016-10-18 10:40:52', NULL, NULL, 0, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `koordinat`
--

CREATE TABLE IF NOT EXISTS `koordinat` (
  `koordinat_id` int(11) NOT NULL,
  `konsumen_id` varchar(50) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `address` varchar(100) NOT NULL,
  `city` varchar(20) NOT NULL,
  `province` varchar(20) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(200) NOT NULL,
  `create_at` datetime NOT NULL,
  `update_at` datetime NOT NULL,
  `maps_status` int(2) NOT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'laundry',
  PRIMARY KEY (`koordinat_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kurir`
--

CREATE TABLE IF NOT EXISTS `kurir` (
  `id` int(10) NOT NULL,
  `kurir_id` varchar(30) NOT NULL,
  `kurir_nama` varchar(100) NOT NULL,
  `kurir_alamat` varchar(100) NOT NULL,
  `kurir_nohp` varchar(50) NOT NULL,
  `kurir_email` varchar(30) NOT NULL,
  `kurir_password_hash` text NOT NULL,
  `kurir_api_key` varchar(100) NOT NULL,
  `kurir_status` int(1) NOT NULL DEFAULT '1',
  `kurir_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `kurir_last_login` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `kurir_updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`kurir_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login_attemps`
--

CREATE TABLE IF NOT EXISTS `login_attemps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `waktu` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `login_attemps`
--

INSERT INTO `login_attemps` (`id`, `email`, `waktu`) VALUES
(1, 'yulynur2013@gmail.com', '1467005385'),
(2, 'yulynur2013@gmail.com', '1467005387'),
(3, 'yulynur2013@gmail.com', '1467005580'),
(4, 'yulynur2013@gmail.com', '1467008543'),
(5, 'yuly@gmail.com', '1467009710'),
(6, 'yuly@gmail.com', '1467012256'),
(7, 'wachid.sst@gmail.com', '1467360680'),
(8, 'wachid.sst@gmail.com', '1467360685'),
(9, 'wachid.sst@gmail.com', '1467360736'),
(10, 'wachid.sst@gmail.com', '1467360744'),
(11, 'wachid.sst@gmail.com', '1468342189'),
(12, 'wachid.sst@gmail.com', '1468384031'),
(13, 'wachid.sst@gmail.com', '1468462436'),
(14, 'wachid.sst@gmail.com', '1468462451'),
(15, 'wachid.sst@gmail.com', '1468570834'),
(16, 'yulynur@gmail.com', '1468735608'),
(17, 'yulynur2013@gmail.com', '1468824862'),
(27, 'yulynur2013@gmail.com', '1468826964'),
(26, 'yulynur2013@gmail.com', '1468826407'),
(24, 'yulynur2013@gmail.com', '1468826335'),
(25, 'yulynur2013@gmail.com', '1468826346'),
(28, 'yuly@gmail.com', '1476764581'),
(29, 'yuly@gmail.com', '1476764594'),
(30, 'yuly@gmail.com', '1476786789'),
(31, 'wachid.sst@gmail.com', '1476787127'),
(32, 'wachid.sst@gmail.com', '1476787143'),
(33, 'kemenhub.ts@gmail.com', '1476788538'),
(34, 'kemenhub.ts@gmail.com', '1476788556');

-- --------------------------------------------------------

--
-- Table structure for table `master_transaksi`
--

CREATE TABLE IF NOT EXISTS `master_transaksi` (
  `NO_FAKTUR` char(19) NOT NULL,
  `TGL` datetime NOT NULL,
  `GRAND_TOTAL` double NOT NULL,
  PRIMARY KEY (`NO_FAKTUR`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `memberID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `active` varchar(255) NOT NULL,
  `resetToken` varchar(255) DEFAULT NULL,
  `resetComplete` varchar(3) DEFAULT 'No',
  PRIMARY KEY (`memberID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`memberID`, `username`, `password`, `email`, `active`, `resetToken`, `resetComplete`) VALUES
(17, 'abdur rochman wachid al firdaus', '$2y$10$C824QdMTlAnPvO26g464NOa3PphURVVntti87.m4YZnMm98ARfps6', 'wachid.sst@gmail.com', 'Yes', 'ab9b66965a0649822f0760e01312a2d1', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `paket`
--

CREATE TABLE IF NOT EXISTS `paket` (
  `paket_id` int(5) NOT NULL,
  `paket_nama` varchar(50) NOT NULL,
  `paket_harga` varchar(10) NOT NULL,
  `paket_keterangan` varchar(100) NOT NULL,
  `paket_status` varchar(5) NOT NULL,
  PRIMARY KEY (`paket_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paket`
--

INSERT INTO `paket` (`paket_id`, `paket_nama`, `paket_harga`, `paket_keterangan`, `paket_status`) VALUES
(1, 'Super Hemat', '2500', 'Paket super hemat, 5 Hari jadi', '1'),
(2, 'Hemat', '4000', 'Paket hemat 4 hari jadi', '1'),
(3, 'Sedang', '5000', 'Paket Sedang, 3 Hari Jadi', '1'),
(4, 'Express', '6000', 'Paket Express, 2 Hari Jadi', '1'),
(5, 'Super Express', '7000', 'Paket Super Express, 1 Hari Jadi', '1');

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_request`
--

CREATE TABLE IF NOT EXISTS `password_reset_request` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `konsumen_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `encrypted_temp_password` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `salt` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=13 ;

--
-- Dumping data for table `password_reset_request`
--

INSERT INTO `password_reset_request` (`sno`, `konsumen_id`, `email`, `encrypted_temp_password`, `salt`, `created_at`) VALUES
(10, '', 'wachid.sst@gmail.com', '$2y$10$eIgbJ4nFyo4F8Qk.Ce.DnOMJjJpXM9Ff3UncaP/f7.e8LcOmGVF7K', 'd2f8e4ed9c', '2016-07-13 09:56:09'),
(12, '', 'yulynur2013@gmail.com', '$2y$10$VNBeCXl9SQA1xS1KJcT8.OtLX6CDVORiYk4qNIA02Z9cDKntIH2V.', '78ff527d07', '2016-07-17 06:07:10');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE IF NOT EXISTS `pembayaran` (
  `pembayaran_id` int(10) NOT NULL AUTO_INCREMENT,
  `penerima_id` varchar(10) NOT NULL,
  `jumlah` varchar(10) NOT NULL,
  `status_pembayaran` varchar(10) NOT NULL,
  PRIMARY KEY (`pembayaran_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pengeluaran`
--

CREATE TABLE IF NOT EXISTS `pengeluaran` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` date NOT NULL,
  `keperluan` text NOT NULL,
  `nama` text NOT NULL,
  `jumlah` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `pengeluaran`
--

INSERT INTO `pengeluaran` (`id`, `tanggal`, `keperluan`, `nama`, `jumlah`) VALUES
(1, '2015-02-06', 'de', 'diki', 1234);

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE IF NOT EXISTS `pesanan` (
  `pesanan_id` int(11) NOT NULL AUTO_INCREMENT,
  `pesanan_nota_id` varchar(100) NOT NULL,
  `konsumen_id` varchar(30) NOT NULL,
  `paket_id` varchar(10) NOT NULL,
  `pesanan_satuan` int(11) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `tanggal_pengantaran` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_cucian` varchar(10) NOT NULL,
  `status_pengantaran` varchar(20) NOT NULL,
  PRIMARY KEY (`pesanan_id`),
  KEY `pesanan_id` (`pesanan_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`pesanan_id`, `pesanan_nota_id`, `konsumen_id`, `paket_id`, `pesanan_satuan`, `alamat`, `tanggal_pengantaran`, `status_cucian`, `status_pengantaran`) VALUES
(1, '', '', '2', 0, '', '0000-00-00 00:00:00', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `status_pencucian`
--

CREATE TABLE IF NOT EXISTS `status_pencucian` (
  `pencucian_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  `admin_id` varchar(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pencucian_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `status_pengantaran`
--

CREATE TABLE IF NOT EXISTS `status_pengantaran` (
  `pengantaran_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
  `sales_id` varchar(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pengantaran_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE IF NOT EXISTS `transaksi` (
  `transaksi_id` int(11) NOT NULL AUTO_INCREMENT,
  `konsumen_id` varchar(50) NOT NULL,
  `pemesanan_id` varchar(20) NOT NULL,
  `kurir_pengambil_id` varchar(50) NOT NULL,
  `kurir_pengantar_id` varchar(50) NOT NULL,
  `tempat_id` varchar(20) NOT NULL,
  `pembayaran_id` varchar(20) NOT NULL,
  `tanggal_transaksi` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaksi_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`transaksi_id`, `konsumen_id`, `pemesanan_id`, `kurir_pengambil_id`, `kurir_pengantar_id`, `tempat_id`, `pembayaran_id`, `tanggal_transaksi`) VALUES
(1, 'a1353a67c290b2287b947a130379f3be', '1', '123', '123', '1', '', '2016-02-24 03:32:19');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(23) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `encrypted_password` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `salt` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
