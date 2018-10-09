-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ott 09, 2018 alle 12:23
-- Versione del server: 10.1.26-MariaDB
-- Versione PHP: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `igwdb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `cdl`
--

CREATE TABLE `cdl` (
  `IDCDL` int(11) NOT NULL,
  `Nome_it` varchar(50) NOT NULL,
  `Nome_en` varchar(50) NOT NULL,
  `Anno` year(4) NOT NULL,
  `CFU` int(11) NOT NULL,
  `Magistrale` tinyint(4) NOT NULL DEFAULT '0',
  `Immagine` varchar(100) DEFAULT NULL,
  `Descrizione_it` longtext,
  `Descrizione_en` longtext,
  `Abbr_it` varchar(5) NOT NULL,
  `Abbr_en` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `colleg_corsi`
--

CREATE TABLE `colleg_corsi` (
  `This_Corso` int(11) NOT NULL,
  `Other_Corso` int(11) NOT NULL,
  `Tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `corsi_cdl`
--

CREATE TABLE `corsi_cdl` (
  `Corso` int(11) NOT NULL,
  `CDL` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

CREATE TABLE `corso` (
  `IDCorso` int(11) NOT NULL,
  `Nome_it` varchar(50) DEFAULT NULL,
  `Nome_en` varchar(50) DEFAULT NULL,
  `SSD` varchar(10) DEFAULT NULL,
  `Lingua` varchar(20) DEFAULT NULL,
  `Semestre` int(11) DEFAULT NULL,
  `CFU` int(11) DEFAULT NULL,
  `Anno` year(4) NOT NULL,
  `Tipologia` char(1) DEFAULT NULL,
  `OldID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `descrizione_en`
--

CREATE TABLE `descrizione_en` (
  `Corso` int(11) NOT NULL,
  `Prerequisiti` text NOT NULL,
  `Obiettivi` text NOT NULL,
  `Mod_Esame` text NOT NULL,
  `Mod_Insegnamento` text NOT NULL,
  `Sillabo` text NOT NULL,
  `Note` text,
  `Homepage` text,
  `Forum` text,
  `Risorse_ext` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `descrizione_it`
--

CREATE TABLE `descrizione_it` (
  `Corso` int(11) NOT NULL,
  `Prerequisiti` text NOT NULL,
  `Obiettivi` text NOT NULL,
  `Mod_Esame` text NOT NULL,
  `Mod_Insegnamento` text NOT NULL,
  `Sillabo` text NOT NULL,
  `Note` text,
  `Homepage` text,
  `Forum` text,
  `Risorse_ext` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `docente`
--

CREATE TABLE `docente` (
  `IDDocente` int(11) NOT NULL,
  `Immagine` text,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Ufficio` varchar(50) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Specializzazione` varchar(50) DEFAULT NULL,
  `Ricerche` text,
  `Pubblicazioni` text,
  `Curriculum` text,
  `Ricevimento` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `docente`
--

INSERT INTO `docente` (`IDDocente`, `Immagine`, `Nome`, `Cognome`, `Email`, `Ufficio`, `Telefono`, `Specializzazione`, `Ricerche`, `Pubblicazioni`, `Curriculum`, `Ricevimento`) VALUES
(1, NULL, 'Marco', 'Autili', 'marco.autili@univaq.it', 'Polo 0', '0862433186', 'Software Engineering And Architecture', 'Progetto IP UE, FP7', 'ha pubblicato una pubblicazione', 'Alto capelli ricci figo', 'Giovedi 8:00-12:00');

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti_corso`
--

CREATE TABLE `docenti_corso` (
  `Corso` int(11) NOT NULL,
  `Docente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `group_services`
--

CREATE TABLE `group_services` (
  `Gruppo` int(11) NOT NULL,
  `Servizio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `gruppo`
--

CREATE TABLE `gruppo` (
  `IDGruppo` int(11) NOT NULL,
  `Nome` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `gruppo`
--

INSERT INTO `gruppo` (`IDGruppo`, `Nome`) VALUES
(1, 'Admin'),
(2, 'Docenti');

-- --------------------------------------------------------

--
-- Struttura della tabella `libri_corso`
--

CREATE TABLE `libri_corso` (
  `Corso` int(11) NOT NULL,
  `Libro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `libro`
--

CREATE TABLE `libro` (
  `IDLibro` int(11) NOT NULL,
  `Autore` varchar(20) NOT NULL,
  `Titolo` varchar(50) NOT NULL,
  `Volume` int(11) DEFAULT NULL,
  `Anno` year(4) NOT NULL,
  `Editore` varchar(50) DEFAULT NULL,
  `Link` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `log`
--

CREATE TABLE `log` (
  `IDLog` int(11) NOT NULL,
  `Utente` int(11) NOT NULL,
  `Data` datetime NOT NULL,
  `Descrizione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `materiale`
--

CREATE TABLE `materiale` (
  `IDMateriale` int(11) NOT NULL,
  `Corso` int(11) DEFAULT NULL,
  `Nome` varchar(20) NOT NULL,
  `Link` text NOT NULL,
  `Descrizione_it` varchar(50) DEFAULT NULL,
  `Descrizione_en` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `servizio`
--

CREATE TABLE `servizio` (
  `IDServizio` int(11) NOT NULL,
  `Script` varchar(50) NOT NULL,
  `Descrizione` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `IDUtente` int(11) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Docente` int(11) DEFAULT NULL,
  `Gruppo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IDUtente`, `Username`, `Password`, `Docente`, `Gruppo`) VALUES
(1, 'autili', 'autili', 1, 2),
(2, 'admin', 'password', NULL, 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `cdl`
--
ALTER TABLE `cdl`
  ADD PRIMARY KEY (`IDCDL`);

--
-- Indici per le tabelle `colleg_corsi`
--
ALTER TABLE `colleg_corsi`
  ADD PRIMARY KEY (`This_Corso`,`Other_Corso`),
  ADD KEY `Other_Corso` (`Other_Corso`);

--
-- Indici per le tabelle `corsi_cdl`
--
ALTER TABLE `corsi_cdl`
  ADD PRIMARY KEY (`Corso`,`CDL`),
  ADD KEY `CDL` (`CDL`);

--
-- Indici per le tabelle `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`IDCorso`),
  ADD UNIQUE KEY `OldID` (`OldID`);

--
-- Indici per le tabelle `descrizione_en`
--
ALTER TABLE `descrizione_en`
  ADD PRIMARY KEY (`Corso`);

--
-- Indici per le tabelle `descrizione_it`
--
ALTER TABLE `descrizione_it`
  ADD PRIMARY KEY (`Corso`);

--
-- Indici per le tabelle `docente`
--
ALTER TABLE `docente`
  ADD PRIMARY KEY (`IDDocente`);

--
-- Indici per le tabelle `docenti_corso`
--
ALTER TABLE `docenti_corso`
  ADD PRIMARY KEY (`Corso`,`Docente`),
  ADD KEY `Docente` (`Docente`);

--
-- Indici per le tabelle `group_services`
--
ALTER TABLE `group_services`
  ADD PRIMARY KEY (`Gruppo`,`Servizio`),
  ADD KEY `Servizio` (`Servizio`);

--
-- Indici per le tabelle `gruppo`
--
ALTER TABLE `gruppo`
  ADD PRIMARY KEY (`IDGruppo`);

--
-- Indici per le tabelle `libri_corso`
--
ALTER TABLE `libri_corso`
  ADD PRIMARY KEY (`Corso`,`Libro`),
  ADD KEY `Libro` (`Libro`);

--
-- Indici per le tabelle `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`IDLibro`);

--
-- Indici per le tabelle `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`IDLog`),
  ADD KEY `Utente` (`Utente`);

--
-- Indici per le tabelle `materiale`
--
ALTER TABLE `materiale`
  ADD PRIMARY KEY (`IDMateriale`),
  ADD KEY `Corso` (`Corso`);

--
-- Indici per le tabelle `servizio`
--
ALTER TABLE `servizio`
  ADD PRIMARY KEY (`IDServizio`),
  ADD UNIQUE KEY `Script_UNIQUE` (`Script`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`IDUtente`),
  ADD UNIQUE KEY `Username_UNIQUE` (`Username`),
  ADD UNIQUE KEY `Docente_UNIQUE` (`Docente`),
  ADD KEY `utente_ibfk_2` (`Docente`),
  ADD KEY `Gruppo` (`Gruppo`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `cdl`
--
ALTER TABLE `cdl`
  MODIFY `IDCDL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `corso`
--
ALTER TABLE `corso`
  MODIFY `IDCorso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `docente`
--
ALTER TABLE `docente`
  MODIFY `IDDocente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `gruppo`
--
ALTER TABLE `gruppo`
  MODIFY `IDGruppo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `libro`
--
ALTER TABLE `libro`
  MODIFY `IDLibro` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `log`
--
ALTER TABLE `log`
  MODIFY `IDLog` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `materiale`
--
ALTER TABLE `materiale`
  MODIFY `IDMateriale` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `servizio`
--
ALTER TABLE `servizio`
  MODIFY `IDServizio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `IDUtente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `colleg_corsi`
--
ALTER TABLE `colleg_corsi`
  ADD CONSTRAINT `colleg_corsi_ibfk_1` FOREIGN KEY (`This_Corso`) REFERENCES `corso` (`IDCorso`),
  ADD CONSTRAINT `colleg_corsi_ibfk_2` FOREIGN KEY (`Other_Corso`) REFERENCES `corso` (`IDCorso`);

--
-- Limiti per la tabella `corsi_cdl`
--
ALTER TABLE `corsi_cdl`
  ADD CONSTRAINT `corsi_cdl_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`),
  ADD CONSTRAINT `corsi_cdl_ibfk_2` FOREIGN KEY (`CDL`) REFERENCES `cdl` (`IDCDL`);

--
-- Limiti per la tabella `corso`
--
ALTER TABLE `corso`
  ADD CONSTRAINT `corso_ibfk_1` FOREIGN KEY (`OldID`) REFERENCES `corso` (`IDCorso`);

--
-- Limiti per la tabella `descrizione_en`
--
ALTER TABLE `descrizione_en`
  ADD CONSTRAINT `descrizione_en_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`);

--
-- Limiti per la tabella `descrizione_it`
--
ALTER TABLE `descrizione_it`
  ADD CONSTRAINT `descrizione_it_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`);

--
-- Limiti per la tabella `docenti_corso`
--
ALTER TABLE `docenti_corso`
  ADD CONSTRAINT `docenti_corso_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`),
  ADD CONSTRAINT `docenti_corso_ibfk_2` FOREIGN KEY (`Docente`) REFERENCES `docente` (`IDDocente`);

--
-- Limiti per la tabella `group_services`
--
ALTER TABLE `group_services`
  ADD CONSTRAINT `group_services_ibfk_1` FOREIGN KEY (`Gruppo`) REFERENCES `gruppo` (`IDGruppo`),
  ADD CONSTRAINT `group_services_ibfk_2` FOREIGN KEY (`Servizio`) REFERENCES `servizio` (`IDServizio`);

--
-- Limiti per la tabella `libri_corso`
--
ALTER TABLE `libri_corso`
  ADD CONSTRAINT `libri_corso_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`),
  ADD CONSTRAINT `libri_corso_ibfk_2` FOREIGN KEY (`Libro`) REFERENCES `libro` (`IDLibro`);

--
-- Limiti per la tabella `log`
--
ALTER TABLE `log`
  ADD CONSTRAINT `log_ibfk_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`IDUtente`);

--
-- Limiti per la tabella `materiale`
--
ALTER TABLE `materiale`
  ADD CONSTRAINT `materiale_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corso` (`IDCorso`);

--
-- Limiti per la tabella `utente`
--
ALTER TABLE `utente`
  ADD CONSTRAINT `utente_ibfk_1` FOREIGN KEY (`Gruppo`) REFERENCES `gruppo` (`IDGruppo`),
  ADD CONSTRAINT `utente_ibfk_2` FOREIGN KEY (`Docente`) REFERENCES `docente` (`IDDocente`);

DELIMITER $$
--
-- Eventi
--
CREATE DEFINER=`root`@`localhost` EVENT `new_corsi_year` ON SCHEDULE EVERY 1 YEAR STARTS '2018-08-01 03:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
			CREATE TEMPORARY TABLE tmpCorso
				SELECT * FROM Corso WHERE Anno=YEAR(NOW())-1;
				UPDATE tmpCorso SET OldID=IDCorso;
				ALTER TABLE tmpCorso DROP IDCorso; # Drop autoincrement field
				UPDATE tmpCorso SET Anno=YEAR(NOW()) WHERE Anno=YEAR(NOW())-1;
				INSERT INTO Corso SELECT 0,tmpCorso.* FROM tmpCorso;
				DROP TEMPORARY TABLE tmpCorso;

			CREATE TEMPORARY TABLE tmpCorsi_CDL
				SELECT corsi_cdl.Corso,CDL FROM Corsi_CDL,Corso WHERE Corso.IDCorso=corsi_cdl.Corso AND Anno=YEAR(NOW())-1;
				UPDATE tmpCorsi_CDL SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=tmpCorsi_CDL.Corso);
				INSERT INTO Corsi_CDL SELECT tmpCorsi_CDL.* FROM tmpCorsi_CDL;
				DROP TEMPORARY TABLE tmpCorsi_CDL;
				
			CREATE TEMPORARY TABLE tmpColleg_Corsi
				SELECT colleg_corsi.* FROM Corso,Colleg_Corsi WHERE IDCorso=This_Corso AND Anno=YEAR(NOW())-1;
				UPDATE tmpColleg_Corsi SET This_Corso=(SELECT IDCorso FROM Corso WHERE OldID=This_Corso), Other_Corso=(SELECT IDCorso FROM Corso WHERE OldID=Other_Corso);
				INSERT INTO Colleg_Corsi SELECT tmpColleg_Corsi.* FROM tmpColleg_Corsi;
				DROP TEMPORARY TABLE tmpColleg_Corsi;
				
			CREATE TEMPORARY TABLE tmpDocenti_Corso
				SELECT Corso,Docente FROM Docenti_Corso,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDocenti_Corso SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Docenti_Corso SELECT tmpDocenti_Corso.* FROM tmpDocenti_Corso;
				DROP TEMPORARY TABLE tmpDocenti_Corso;
				
			CREATE TEMPORARY TABLE tmpDescrizione_it
				SELECT Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_ext FROM Descrizione_it,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDescrizione_it SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Descrizione_it SELECT tmpDescrizione_it.* FROM tmpDescrizione_it;
				DROP TEMPORARY TABLE tmpDescrizione_it;
				
			CREATE TEMPORARY TABLE tmpDescrizione_en
				SELECT Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_ext FROM Descrizione_en,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDescrizione_en SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Descrizione_en SELECT tmpDescrizione_en.* FROM tmpDescrizione_en;
				DROP TEMPORARY TABLE tmpDescrizione_en;
				
			CREATE TEMPORARY TABLE tmpDublino_it
				SELECT Corso,Knowledge,Application,Evaluation,Communication,Lifelong FROM Dublino_it,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDublino_it SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Dublino_it SELECT tmpDublino_it.* FROM tmpDublino_it;
				DROP TEMPORARY TABLE tmpDublino_it;
				
			CREATE TEMPORARY TABLE tmpDublino_en
				SELECT Corso,Knowledge,Application,Evaluation,Communication,Lifelong FROM Dublino_en,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDublino_en SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Dublino_en SELECT tmpDublino_en.* FROM tmpDublino_en;
				DROP TEMPORARY TABLE tmpDublino_en;
				
			CREATE TEMPORARY TABLE tmpMateriale
				SELECT IDMateriale,Corso,Nome,Link,Descrizione_it,Descrizione_en FROM Materiale,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpMateriale SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				ALTER TABLE tmpMateriale DROP IDMateriale;
				INSERT INTO Materiale SELECT 0,tmpMateriale.* FROM tmpMateriale;
				DROP TEMPORARY TABLE tmpMateriale;
				
			CREATE TEMPORARY TABLE tmpLibri_Corso
				SELECT Corso,Libro FROM Libri_Corso,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpLibri_Corso SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Libri_Corso SELECT tmpLibri_Corso.* FROM tmpLibri_Corso;
				DROP TEMPORARY TABLE tmpLibri_Corso;
		END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
