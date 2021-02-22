--Drop tables
DROP TABLE Invoice;
DROP TABLE ServiceRequired;
DROP TABLE Appointment;
DROP TABLE ServiceProvider;
DROP TABLE Service;
DROP TABLE DoctorQualification;
DROP TABLE DoctorRoom;
DROP TABLE Room;
DROP TABLE Doctor;
DROP TABLE Patient;
DROP TABLE CustomerContact;
DROP TABLE ResponsibleCustomer;
DROP TABLE Customer;

--Create tables
CREATE TABLE Customer (
clientNumber	NUMERIC(8) NOT NULL,
familyName		VARCHAR(20) NOT NULL,	
personalName	VARCHAR(20) NOT NULL,
title			VARCHAR(4) NOT NULL,
streetAddress	VARCHAR(45) NOT NULL,
postcode		VARCHAR(4) NOT NULL,
Primary key (clientNumber)
);

CREATE TABLE ResponsibleCustomer (
clientNumber	NUMERIC(8) NOT NULL,
email			VARCHAR(50) NOT NULL,
Primary key (clientNumber)
);

CREATE TABLE CustomerContact (
clientNumber	NUMERIC(8) NOT NULL,
phoneNumber		VARCHAR(14) NOT NULL,
Primary key (clientNumber, phoneNumber),
Foreign key (clientNumber) references ResponsibleCustomer(clientNumber) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE Patient (
clientNumber 				NUMERIC(8) NOT NULL,
birthDate 					DATE NOT NULL,
medicareNumber 				VARCHAR(12) NOT NULL,
resonsibleCustomerNumber    NUMERIC(8) NOT NULL,
Primary key (clientNumber),
Foreign key (resonsibleCustomerNumber) references ResponsibleCustomer(clientNumber) ON UPDATE CASCADE ON DELETE NO ACTION 
);

CREATE TABLE Doctor (
uniqueidentifierCode		VARCHAR(2) NOT NULL,
familyName					VARCHAR(20) NOT NULL,	
personalName				VARCHAR(20) NOT NULL,	
title						VARCHAR(4) NOT NULL,	
contactPhoneNumber			VARCHAR(14) NOT NULL,
Primary key (uniqueidentifierCode)
);

CREATE TABLE Room (
roomNumber		NUMERIC(3) NOT NULL,
Primary key (roomNumber)
);

CREATE TABLE DoctorRoom (
uniqueidentifierCode	VARCHAR(2) NOT NULL,	
roomNumber				NUMERIC(3) NOT NULL,
preferredRoom			BOOLEAN,
Primary key (uniqueidentifierCode, roomNumber),
Foreign key (uniqueidentifierCode) references Doctor(uniqueidentifierCode) ON UPDATE CASCADE ON DELETE NO ACTION ,
Foreign Key (roomNumber) references Room(roomNumber) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE DoctorQualification (
uniqueidentifierCode	VARCHAR(2) NOT NULL,
qualification			VARCHAR(10) NOT NULL,
Primary key (uniqueidentifierCode, qualification),
Foreign key (uniqueidentifierCode) references Doctor(uniqueidentifierCode) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE Service (
prescribedCode			VARCHAR(4) NOT NULL,
description				VARCHAR(20) NOT NULL,
currentServiceFee		DECIMAL(3,2) NOT NULL,
Primary key (prescribedCode)
);

CREATE TABLE ServiceProvider (
prescribedCode			VARCHAR(4) NOT NULL,
uniqueidentifierCode	VARCHAR(2) NOT NULL,
Primary key (uniqueidentifierCode, prescribedCode),
Foreign key (uniqueidentifierCode) references Doctor(uniqueidentifierCode) ON UPDATE CASCADE ON DELETE NO ACTION ,
Foreign key (prescribedCode) references Service(prescribedCode) ON UPDATE CASCADE ON DELETE NO ACTION 
);

CREATE TABLE Appointment (
appointmentID			VARCHAR(8) NOT NULL,
dateAndStartingTime		TIMESTAMP NOT NULL,
clientNumber			NUMERIC(8) NOT NULL,
roomNumber				NUMERIC(3) NOT NULL,
uniqueidentifierCode 	VARCHAR(2) NOT NULL,
Primary key (appointmentID),
Foreign key (clientNumber) references Patient(clientNumber) ON UPDATE CASCADE ON DELETE NO ACTION ,
Foreign key (roomNumber) references Room(roomNumber) ON UPDATE CASCADE ON DELETE NO ACTION,
Foreign key (uniqueidentifierCode) references Doctor(uniqueidentifierCode) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE ServiceRequired (
appointmentID		VARCHAR(8) NOT NULL,
prescribedCode		VARCHAR(4) NOT NULL,
actualFee			DECIMAL(3,2) NOT NULL,		
Primary key (appointmentID, prescribedCode),
Foreign key (appointmentID) references Appointment(appointmentID) ON UPDATE CASCADE ON DELETE NO ACTION,
Foreign key (prescribedCode) references Service(prescribedCode) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Invoice (
uniqueInvoiceNumber NUMERIC(5) NOT NULL,
appointmentID 		VARCHAR(8) NOT NULL,
relevantDate 		DATE NOT NULL,
clinicalComment		VARCHAR(200), 
status				CHAR(1) DEFAULT 'C' NOT NULL check(status = 'C' or status = 'B' or status = 'P'),
Primary key (uniqueInvoiceNumber),
Foreign key (appointmentID) references Appointment(appointmentID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- View for totalFee
CREATE VIEW   InvoiceFee
AS  SELECT uniqueInvoiceNumber, i.appointmentID, relevantDate, status, sum(actualFee) as 'Total Fee'
		FROM Invoice i, ServiceRequired s
		WHERE i.appointmentID = s.appointmentID
        GROUP BY s.appointmentID;
 
 ----------CONSTRAINTS----------
 -- phone number CONSTRAINT
 create trigger maximum_three_phone_numbers
 	before insert on CustomerContact
 	begin 
 	select
 	case
		when (select count(*)
 			from CustomerContact
 			where clientNumber = new.clientNumber
 			group by clientNumber) = 3
 		then
 	 		raise(abort, 'Maximum three phone numbers reached')
 	end;
 	end;

 -- qualification CONSTRAINT
 create trigger maximum_three_qualifications
 	before insert on DoctorQualification
 	begin 
 	select
 	case
		when (select count(*)
 			from DoctorQualification
 			where uniqueidentifierCode = new.uniqueidentifierCode
 			group by uniqueidentifierCode) = 3
 		then
 	 		raise(abort, 'Maximum three qualifications reached')
 	end;
 	end;

 -- appointment services CONSTRAINT
 create trigger maximum_five_services
 	before insert on ServiceRequired
 	begin 
 	select
 	case
		when (select count(*)
 			from ServiceRequired
 			where appointmentID = new.appointmentID
 			group by appointmentID) = 5
 		then
 	 		raise(abort, 'Maximum five services for an appointment reached')
 	end;
 	end;

 -- room nomination CONSTRAINT
 create trigger maximum_ten_nominated_rooms
 	before insert on DoctorRoom
 	begin 
 	select
 	case
		when (select count(*)
 			from DoctorRoom
 			where uniqueidentifierCode = new.uniqueidentifierCode
 			group by uniqueidentifierCode) = 10
 		then
 	 		raise(abort, 'Maximum ten nominated rooms reached')
 	end;
 	end;

 -- only one preferred room CONSTRAINT
 create trigger one_preffered_room
 	before insert on DoctorRoom
 	begin 
 	select
 	case
		when (select count(*)
 			from DoctorRoom
 			where uniqueidentifierCode = new.uniqueidentifierCode and preferredRoom = True
 			group by uniqueidentifierCode) = 1
			and (new.preferredRoom = True)
 		then
 	 		raise(abort, 'Only one preferred room allowed')
 	end;
 	end;

---------------DATA---------------
insert into Customer values (10000001, 'Trump', 'Donald', 'Mr', '2863  University Street', '5042');
insert into Customer values (10000002, 'Lewis', 'Trent', 'Mr', '197 Gover St, North Adelaide', '5006');
insert into Customer values (10000003, 'Dion', 'Celine', 'Mrs', '141 East Tce, Adelaide', '5000');	
insert into Customer values (10000004, 'Swift', 'Taylor', 'Mrs', '212 Gilles St, Adelaide', '5000');
insert into Customer values (10000005, 'Legend', 'John', 'Mr', '5 Francis St, North Adelaide', '5006');
insert into Customer values (10000006, 'Puth', 'Charlie', 'Mr', '181 Unley Road, Unley', '5061');
insert into Customer values (10000007, 'Perry', 'Katy', 'Mrs', '234 Seaside St, Glenelg', '5045');
insert into Customer values	(10000008, 'Gomez', 'Selena', 'Mrs','71 Richmond Rd, Mile End South', '5031');

insert into ResponsibleCustomer values (10000001, 'whitehouse@gmail.com');
insert into ResponsibleCustomer values (10000002, 'universtity@gmail.com');
insert into ResponsibleCustomer values (10000005, 'legend@gmail.com');
insert into ResponsibleCustomer values (10000006, 'music@gmail.com');
insert into ResponsibleCustomer values (10000007, 'concert@gmail.com');

insert into CustomerContact values (10000001, '5555-1239-1233');
insert into CustomerContact values (10000001, '2121-9834-0089');
insert into CustomerContact values (10000001, '9876-1023-8293');
insert into CustomerContact values (10000002, '3333-1231-4581');
insert into CustomerContact values (10000005, '3344-2351-6567');
insert into CustomerContact values (10000005, '4561-5913-4911');
insert into CustomerContact values (10000006, '9872-9882-3413');
insert into CustomerContact values (10000007, '1234-9425-3426');

insert into Patient values (10000001, '1990-02-01', 'R1234-567-89', '10000001');
insert into Patient values (10000002, '1980-02-01', 'E4321-251-83', '10000001');
insert into Patient values (10000003, '1990-02-01', 'S7633-786-13', '10000005');
insert into Patient values (10000004, '1990-02-01', 'A3213-411-54', '10000007');
insert into Patient values (10000006, '1990-02-01', 'G4521-134-61', '10000006');
insert into Patient values (10000008, '1990-02-01', 'R1546-762-56', '10000007');

insert into Doctor values ('A1', 'Djokovic', 'Novak', 'Mr', '9821-4518-2347');
insert into Doctor values ('A2', 'Sharapova', 'Maria', 'Mrs', '0932-2341-3514');
insert into Doctor values ('A3', 'Williams', 'Selena', 'Mrs', '1239-3498-1234');
insert into Doctor values ('B1', 'Federer', 'Roger', 'Mr', '2349-2341-9851');
insert into Doctor values ('B2', 'Nadal', 'Rafael', 'Mr', '1491-4551-4520');
insert into Doctor values ('C1', 'Murray', 'Andy', 'Mr', '1538-3982-3411');
insert into Doctor values ('C2', 'Jordan', 'Micheal', 'Mr', '6635-8876-2234');

insert into Room values ('101');
insert into Room values ('201');
insert into Room values ('301');
insert into Room values ('401');
insert into Room values ('501');
insert into Room values ('601');
insert into Room values ('701');

--a1
insert into DoctorRoom values ('A1', '101', False);
insert into DoctorRoom values ('A1', '301', False);
insert into DoctorRoom values ('A1', '401', False);
insert into DoctorRoom values ('A1', '701', True);
--a2
insert into DoctorRoom values ('A2', '201', False);
insert into DoctorRoom values ('A2', '601', True);
insert into DoctorRoom values ('A2', '701', False);
--a3
insert into DoctorRoom values ('A3', '301', True);
insert into DoctorRoom values ('A3', '401', False);
insert into DoctorRoom values ('A3', '501', False);
--b1
insert into DoctorRoom values ('B1', '101', True);
insert into DoctorRoom values ('B1', '201', False);
insert into DoctorRoom values ('B1', '401', False);
insert into DoctorRoom values ('B1', '601', False);
--b2
insert into DoctorRoom values ('B2', '201', False);
insert into DoctorRoom values ('B2', '501', False);
insert into DoctorRoom values ('B2', '601', True);
--c1
insert into DoctorRoom values ('C1', '101', False);
insert into DoctorRoom values ('C1', '301', True);
insert into DoctorRoom values ('C1', '501', False);
--c2
insert into DoctorRoom values ('C2', '201', True);
insert into DoctorRoom values ('C2', '301', False);
insert into DoctorRoom values ('C2', '501', False);
insert into DoctorRoom values ('C2', '601', True);


insert into DoctorQualification values ('A1', 'Ophthalmology');
insert into DoctorQualification values ('A1', 'Surgery');
insert into DoctorQualification values ('A1', 'Psychiatry');
insert into DoctorQualification values ('A2', 'Neurosurgery');
insert into DoctorQualification values ('A2', 'Psychiatry');
insert into DoctorQualification values ('A3', 'Dermatology'); 
insert into DoctorQualification values ('A3', 'Psychiatry');
insert into DoctorQualification values ('B1', 'Surgery');
insert into DoctorQualification values ('B2', 'Dentistry');
insert into DoctorQualification values ('B2', 'Ophthalmology');
insert into DoctorQualification values ('B3', 'Dermatology');

insert into Service values ('1234', 'Flu Vaccinations', '150.00');
insert into Service values ('4321', 'Chiropractic Care', '170.75');
insert into Service values ('6789', 'Eye Services', '230.25');
insert into Service values ('4644', 'Wound Care', '40.95');
insert into Service values ('9976', 'Wellness Coaching', '285.15');
insert into Service values ('6654', 'Social Services', '175.77');
insert into Service values ('3566', 'Respiratory Therapy', '90.15');


insert into ServiceProvider values ('1234', 'A1');
insert into ServiceProvider values ('4321', 'A2');
insert into ServiceProvider values ('6789', 'A3');
insert into ServiceProvider values ('4644', 'B1');
insert into ServiceProvider values ('9976', 'B2');
insert into ServiceProvider values ('6654', 'C1');
insert into ServiceProvider values ('3566', 'C2');

insert into Appointment values ('FGC43527', '2020-10-31 09:00:00', '10000001', '101', 'A1');
insert into Appointment values ('FGC99876', '2020-11-11 16:00:00', '10000003', '301', 'A2');
insert into Appointment values ('FGC66735', '2020-05-17 15:00:00', '10000005', '201', 'A3');
insert into Appointment values ('FGC44332', '2020-11-19 11:00:00', '10000002', '401', 'C1');
insert into Appointment values ('FGC77763', '2020-12-03 17:00:00', '10000006', '501', 'B1');
insert into Appointment values ('FGC99127', '2021-01-01 10:00:00', '10000007', '701', 'B2');
insert into Appointment values ('FGC77662', '2021-08-15 14:00:00', '10000008', '601', 'C2');

-- required services for appointments
insert into ServiceRequired values ('FGC43527', '1234', '150.00');
insert into ServiceRequired values ('FGC43527', '9976', '0');
insert into ServiceRequired values ('FGC43527', '3566', '90.15');
--
insert into ServiceRequired values ('FGC99876', '3566', '90.15');
--
insert into ServiceRequired values ('FGC66735', '4321', '170.75');
insert into ServiceRequired values ('FGC66735', '3566', '0');
insert into ServiceRequired values ('FGC66735', '6789', '230.25');
insert into ServiceRequired values ('FGC66735', '4644', '0');
--
insert into ServiceRequired values ('FGC44332', '6789', '0');
--
insert into ServiceRequired values ('FGC77763', '4644', '45.00');
insert into ServiceRequired values ('FGC77763', '9976', '285.15');
--
insert into ServiceRequired values ('FGC99127', '9976', '285.15');
--
insert into ServiceRequired values ('FGC77662', '6654', '0'); 
insert into ServiceRequired values ('FGC77662', '4321', '170.75');
insert into ServiceRequired values ('FGC77662', '3566', '90.15');
insert into ServiceRequired values ('FGC77662', '6789', '0');


insert into Invoice values ('12121', 'FGC43527', '2020-11-02', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit', 'C');
insert into Invoice values ('13131', 'FGC99876', '2020-12-12', 'Sed sed diam ac nibh ornare sagittis in quis erat', 'B');
insert into Invoice values ('15151', 'FGC44332', '2020-12-19', null, 'P'); 
insert into Invoice values ('14141', 'FGC66735', '2020-06-12', 'Donec id diam at magna pulvinar iaculis a at diam', 'C');
insert into Invoice values ('16161', 'FGC77763', '2020-12-22', null, 'B');
insert into Invoice values ('17171', 'FGC99127', '2021-01-03', 'Nullam diam leo, elementum in accumsan pharetra, vestibulum vel mauris', 'P');
insert into Invoice values ('18181', 'FGC77662', '2021-08-19', 'Nam maximus consectetur augue ac condimentum', 'C');
