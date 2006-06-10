-- phpMyAdmin SQL Dump
-- version LocaWeb
-- http://www.phpmyadmin.net
-- 
-- Servidor: localhost
-- Tempo de Geração: Mai 28, 2006 as 10:58 PM
-- Versão do Servidor: 4.1.19
-- Versão do PHP: 4.3.2
-- 
-- Banco de Dados: `avesbrasil1`
-- 

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `admin`
-- 

CREATE TABLE `admin` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `age`
-- 

CREATE TABLE `age` (
  `id` tinyint(1) NOT NULL default '0',
  `age` varchar(10) collate latin1_general_ci NOT NULL default ''
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `city`
-- 

CREATE TABLE `city` (
  `id` int(7) NOT NULL auto_increment,
  `state_id` tinyint(2) NOT NULL default '0',
  `name` varchar(64) collate latin1_general_ci NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=10000 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `common_name`
-- 

CREATE TABLE `common_name` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(64) collate latin1_general_ci NOT NULL default '',
  PRIMARY KEY  (`id`),
  KEY `common_name_index_name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=3489 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `family`
-- 

CREATE TABLE `family` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(64) collate latin1_general_ci NOT NULL default '',
  `subFamilyName` varchar(64) collate latin1_general_ci default NULL,
  PRIMARY KEY  (`id`),
  KEY `familia_index_name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=208 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `photo`
-- 

CREATE TABLE `photo` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL default '0',
  `specie_family_id` int(10) NOT NULL default '0',
  `specie_id` int(10) NOT NULL default '0',
  `date` datetime default NULL,
  `place` text collate latin1_general_ci,
  `city_id` int(7) default NULL,
  `camera` varchar(16) collate latin1_general_ci default NULL,
  `lens` varchar(32) collate latin1_general_ci default NULL,
  `film` varchar(64) collate latin1_general_ci default NULL,
  `image` mediumblob NOT NULL,
  `w` smallint(6) NOT NULL default '0',
  `h` smallint(6) NOT NULL default '0',
  `smallImage` mediumblob,
  `sW` smallint(6) NOT NULL default '0',
  `sH` smallint(6) NOT NULL default '0',
  `imageSize` int(10) NOT NULL default '0',
  `smallImageSize` int(10) NOT NULL default '0',
  `post_date` datetime default NULL,
  `comment` text collate latin1_general_ci,
  `age_id` tinyint(1) default '2',
  `sex_id` tinyint(1) default '1',
  PRIMARY KEY  (`id`),
  KEY `photo_index_user_id` (`user_id`),
  KEY `photo_FKIndex2` (`specie_id`,`specie_family_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=851 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `photo_identify`
-- 

CREATE TABLE `photo_identify` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL default '0',
  `date` datetime default NULL,
  `place` varchar(32) collate latin1_general_ci default NULL,
  `city_id` int(7) default NULL,
  `camera` varchar(16) collate latin1_general_ci default NULL,
  `lens` varchar(32) collate latin1_general_ci default NULL,
  `film` varchar(64) collate latin1_general_ci default NULL,
  `image` mediumblob NOT NULL,
  `w` smallint(6) NOT NULL default '0',
  `h` smallint(6) NOT NULL default '0',
  `smallImage` mediumblob,
  `sW` smallint(6) NOT NULL default '0',
  `sH` smallint(6) NOT NULL default '0',
  `imageSize` int(10) NOT NULL default '0',
  `smallImageSize` int(10) NOT NULL default '0',
  `post_date` datetime default NULL,
  `comment` text collate latin1_general_ci,
  PRIMARY KEY  (`id`),
  KEY `photo_index_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=632 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `sex`
-- 

CREATE TABLE `sex` (
  `id` tinyint(1) NOT NULL default '0',
  `description` varchar(10) collate latin1_general_ci NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `sound`
-- 

CREATE TABLE `sound` (
  `id` int(11) NOT NULL auto_increment,
  `specie_id` int(11) NOT NULL default '0',
  `user_id` int(10) NOT NULL default '0',
  `age_id` tinyint(1) NOT NULL default '0',
  `sex_id` tinyint(1) NOT NULL default '0',
  `fileSize` int(11) NOT NULL default '0',
  `location` varchar(64) collate latin1_general_ci default NULL,
  `city_id` tinyint(2) default NULL,
  `post_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `comment` text collate latin1_general_ci,
  PRIMARY KEY  (`id`),
  KEY `specie_id` (`specie_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `specie`
-- 

CREATE TABLE `specie` (
  `id` int(10) NOT NULL auto_increment,
  `family_id` int(10) NOT NULL default '0',
  `name` varchar(64) collate latin1_general_ci NOT NULL default '',
  PRIMARY KEY  (`id`,`family_id`),
  KEY `specie_index_name` (`name`),
  KEY `specie_index_family_id` (`family_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=5382 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `specie_has_common_name`
-- 

CREATE TABLE `specie_has_common_name` (
  `specie_id` int(10) unsigned NOT NULL default '0',
  `common_name_id` int(10) unsigned NOT NULL default '0',
  `specie_family_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`specie_id`,`common_name_id`,`specie_family_id`),
  KEY `specie_has_common_name_index_specie_id` (`specie_id`,`specie_family_id`),
  KEY `specie_has_common_name_index_common_name_id` (`common_name_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `states`
-- 

CREATE TABLE `states` (
  `id` tinyint(2) NOT NULL default '0',
  `acronym` char(3) collate latin1_general_ci NOT NULL default '',
  `description` varchar(32) collate latin1_general_ci NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `acronym` (`acronym`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `user`
-- 

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(64) collate latin1_general_ci NOT NULL default '',
  `login` varchar(16) collate latin1_general_ci NOT NULL default '',
  `password` varchar(16) collate latin1_general_ci NOT NULL default '',
  `email` varchar(64) collate latin1_general_ci NOT NULL default '',
  `birthday` date default NULL,
  `emailOnNewPhoto` tinyint(1) NOT NULL default '0',
  `emailOnNewIdPhoto` tinyint(1) NOT NULL default '0',
  `emailOnNewSound` tinyint(1) default '0',
  `addPhoto` tinyint(1) NOT NULL default '0',
  `addSound` tinyint(1) NOT NULL default '0',
  `admin` tinyint(1) NOT NULL default '0',
  `language` varchar(5) collate latin1_general_ci default 'pt',
  PRIMARY KEY  (`id`),
  KEY `user_index_name` (`name`),
  KEY `user_index_login` (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=173 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `user_comments_photo`
-- 

CREATE TABLE `user_comments_photo` (
  `id` int(10) NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL default '0',
  `photo_id` int(10) unsigned NOT NULL default '0',
  `comment` text collate latin1_general_ci NOT NULL,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=122 ;

-- --------------------------------------------------------

-- 
-- Estrutura da tabela `user_identifies_photo`
-- 

CREATE TABLE `user_identifies_photo` (
  `id` int(10) NOT NULL auto_increment,
  `user_id` int(10) NOT NULL default '0',
  `photo_id` int(10) NOT NULL default '0',
  `specie_id` int(10) NOT NULL default '0',
  `sex_id` tinyint(4) NOT NULL default '0',
  `age_id` tinyint(4) NOT NULL default '0',
  `comment` text collate latin1_general_ci,
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=18 ;
