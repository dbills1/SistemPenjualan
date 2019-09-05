# Host: 127.0.0.1  (Version 5.5.5-10.1.33-MariaDB)
# Date: 2018-07-16 05:17:31
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "detil_kwitansi"
#

DROP TABLE IF EXISTS `detil_kwitansi`;
CREATE TABLE `detil_kwitansi` (
  `no_kwitansi` varchar(7) NOT NULL,
  `kd_sparepart` varchar(5) NOT NULL,
  `jumlahpesan` int(3) DEFAULT NULL,
  `hargapesan` int(8) DEFAULT NULL,
  PRIMARY KEY (`no_kwitansi`,`kd_sparepart`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "detil_kwitansi"
#

INSERT INTO `detil_kwitansi` VALUES ('KW00001','3',5,150000);

#
# Structure for table "detil_penjualan"
#

DROP TABLE IF EXISTS `detil_penjualan`;
CREATE TABLE `detil_penjualan` (
  `no_penjualan` varchar(10) NOT NULL DEFAULT '',
  `kd_sparepart` varchar(5) NOT NULL DEFAULT '',
  `Jml` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`no_penjualan`,`kd_sparepart`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "detil_penjualan"
#

INSERT INTO `detil_penjualan` VALUES ('1','1',2,10000);

#
# Structure for table "detil_sparepart"
#

DROP TABLE IF EXISTS `detil_sparepart`;
CREATE TABLE `detil_sparepart` (
  `no_wo` varchar(10) NOT NULL DEFAULT '',
  `kd_sparepart` varchar(5) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  `tgl_penjualan` date DEFAULT NULL,
  PRIMARY KEY (`no_wo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "detil_sparepart"
#

INSERT INTO `detil_sparepart` VALUES ('1','1',4,20000,'2018-07-02');

#
# Structure for table "detil_wo"
#

DROP TABLE IF EXISTS `detil_wo`;
CREATE TABLE `detil_wo` (
  `no_wo` varchar(10) NOT NULL DEFAULT '',
  `kd_service` varchar(5) NOT NULL DEFAULT '',
  `harga_service` int(10) DEFAULT NULL,
  PRIMARY KEY (`no_wo`,`kd_service`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "detil_wo"
#

INSERT INTO `detil_wo` VALUES ('WO00002','1',2500),('WO00002','2',40000),('WO00003','2',40000),('WO00003','3',50000),('WO00004','2',40000),('WO00004','3',50000),('WO00005','1',2500),('WO00005','2',40000),('WO00006','2',40000),('WO00006','3',50000),('WO00007','3',50000),('WO00007','4',145000),('WO00008','1',2500),('WO00008','2',40000),('WO00009','3',50000),('WO00009','5',800000),('WO00010','1',2500),('WO00010','4',145000);

#
# Structure for table "kwitansi"
#

DROP TABLE IF EXISTS `kwitansi`;
CREATE TABLE `kwitansi` (
  `no_kwitansi` varchar(10) NOT NULL DEFAULT '',
  `no_wo` varchar(10) NOT NULL DEFAULT '',
  `tgl_kwitansi` date DEFAULT NULL,
  PRIMARY KEY (`no_kwitansi`,`no_wo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "kwitansi"
#

INSERT INTO `kwitansi` VALUES ('KW00001','WO00006','2018-07-14');

#
# Structure for table "mekanik"
#

DROP TABLE IF EXISTS `mekanik`;
CREATE TABLE `mekanik` (
  `kd_mekanik` varchar(5) NOT NULL DEFAULT '',
  `nama_mekanik` varchar(40) DEFAULT NULL,
  `alamat` varchar(70) DEFAULT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`kd_mekanik`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "mekanik"
#

INSERT INTO `mekanik` VALUES ('1','kentung','kepo dah lu\t','0888888888'),('2','Siapa Kek','jlhgf','087644677'),('3','hhghg','asd','0987654321'),('4','dsafsa','qeqweq','123456789'),('5','billy','tangerang','019283091');

#
# Structure for table "motor"
#

DROP TABLE IF EXISTS `motor`;
CREATE TABLE `motor` (
  `kd_motor` varchar(5) NOT NULL DEFAULT '',
  `plat_motor` varchar(10) DEFAULT NULL,
  `merk` varchar(20) DEFAULT NULL,
  `warna` varchar(15) DEFAULT NULL,
  `tahun` varchar(4) DEFAULT NULL,
  `nama_pemilik` varchar(40) DEFAULT NULL,
  `alamat` varchar(70) DEFAULT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`kd_motor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "motor"
#

INSERT INTO `motor` VALUES ('3','B 987 JK','Kawasakti','ping','1820','dmna aja','088881234','b11A'),('4','B1117','Sizuka','merah jambret','1901','kancutlonggar','Jl.dimana aja','0877777777'),('5','B1234AS','vespa','pink','1990','jaka','kplp','0888888888'),('6','b153','kkk','jjjj','1432','hhhhh','jlll','08765432'),('7','B1511AA','Honda','Hitam','2011','IbuDosen','Univ.Budiluhur','08775788897'),('8','B0987LL','hgf','hhih','1209','kkkk','jjjjj','09766554');

#
# Structure for table "penjualan"
#

DROP TABLE IF EXISTS `penjualan`;
CREATE TABLE `penjualan` (
  `no_penjualan` varchar(10) NOT NULL DEFAULT '',
  `tgl_penjualan` date DEFAULT NULL,
  `Total_penjualan` int(11) DEFAULT NULL,
  PRIMARY KEY (`no_penjualan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "penjualan"
#

INSERT INTO `penjualan` VALUES ('1','2018-07-01',350000),('2','2018-07-03',500000);

#
# Structure for table "service"
#

DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `kd_service` varchar(5) NOT NULL DEFAULT '',
  `nama_service` varchar(40) DEFAULT NULL,
  `harga_service` int(11) DEFAULT NULL,
  PRIMARY KEY (`kd_service`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "service"
#

INSERT INTO `service` VALUES ('1','kilik',2500),('2','service',40000),('3','Tune Up',50000),('4','Turun berok',145000),('5','mesin samping',800000);

#
# Structure for table "sparepart"
#

DROP TABLE IF EXISTS `sparepart`;
CREATE TABLE `sparepart` (
  `kd_sparepart` varchar(5) NOT NULL DEFAULT '',
  `nama_sparepart` varchar(40) DEFAULT NULL,
  `harga_sparepart` int(11) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  PRIMARY KEY (`kd_sparepart`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "sparepart"
#

INSERT INTO `sparepart` VALUES ('1','Baut',5000,1),('2','bohlam',250000,0),('3','kampas',30000,6),('4','spion',67000,8),('5','sil oli',25000,46);

#
# Structure for table "wo"
#

DROP TABLE IF EXISTS `wo`;
CREATE TABLE `wo` (
  `no_wo` varchar(8) NOT NULL DEFAULT '',
  `tgl_wo` date DEFAULT NULL,
  `keluhan` text,
  `jarak_tempuh` int(11) DEFAULT NULL,
  `plat_motor` varchar(10) DEFAULT NULL,
  `kd_mekanik` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`no_wo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "wo"
#

INSERT INTO `wo` VALUES ('WO00001','2018-07-12','fhgfgh',6000,'B1234AS','1'),('WO00002','2018-07-12','asd',8000,'B1117','1'),('WO00003','2018-07-12','bocor oli sil',8900,'B1234AS','1'),('WO00004','2018-07-12','asd',10000,'B1117','2'),('WO00005','2018-07-12','asd',90000,'B1117','2'),('WO00006','2018-07-12','sil oli bocor',8787,'B1234AS','5'),('WO00007','2018-07-13','hbhbkkbkbkbk',7000,'B0987LL','1'),('WO00008','2018-07-13','hhhhhh',8888,'B 987 JK','2'),('WO00009','2018-07-14','asd',10000,'B1511AA','2'),('WO00010','2018-07-14','asd',12345,'B 987 JK','1');
