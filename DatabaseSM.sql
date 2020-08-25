--Tạo CSDL
create database StudentManager
go
use StudentManager
go

--Tạo bảng
create table tblUser (
	idUser int identity(1,1) primary key,
	username varchar(50) unique,
	password varchar(50),
	modifiedDate datetime default(getdate())  
)
go

create table tblTeacher (
	idTeacher int identity(1,1) primary key,
	rollNumber varchar(20),
	firstName nvarchar(30),
	lastName nvarchar(30),
	gender bit,
	phone varchar(15),
	email varchar(70),
	address nvarchar(30),
	modifiedDate datetime default(getdate())  
)
go

CREATE TABLE ##tblTeacher (
	rollNumber varchar(20),
	firstName nvarchar(30),
	lastName nvarchar(30),
	gender bit,
	phone varchar(15),
	email varchar(70),
	address nvarchar(30),
	modifiedDate datetime default(getdate())  
);

create table tblSubject (
	idSubject int identity(1,1) primary key,
	subjectName nvarchar(90),
	fee float,
	modifiedDate datetime default(getdate())  
)
go

CREATE TABLE ##tblSubject (
	subjectName nvarchar(90),
	fee float,
	modifiedDate datetime default(getdate())  
);

create table tblClasses (
	idClass int identity(1,1) primary key,
	className nvarchar(64),
	numberOfStudents int,
	modifiedDate datetime default(getdate())  
)
go

CREATE TABLE classesTemp (
	idClass int identity(1,1) primary key,
	className nvarchar(64),
	numberOfStudents int,
	modifiedDate datetime default(getdate())  
);

create table tblStudent (
	idStudent int identity(1,1) primary key,
	idClass int foreign key references tblClasses(idClass),
	rollNumber varchar(20),
	firstName nvarchar(30),
	lastName nvarchar(30),
	gender bit,
	birthDate date,
	phone varchar(15),
	email varchar(70),
	address nvarchar(30),
	modifiedDate datetime default(getdate()) 
)
go

CREATE TABLE studentTemp (
	idStudent int identity(1,1) primary key,
	className nvarchar(64),
	rollNumber varchar(20),
	firstName nvarchar(30),
	lastName nvarchar(30),
	gender bit,
	birthDate date,
	phone varchar(15),
	email varchar(70),
	address nvarchar(30),
	modifiedDate datetime default(getdate()) 
); 

create table tblScore(
	idScore int identity(1,1) primary key,
	idStudent int foreign key references tblStudent(idStudent),
	idSubject int foreign key references tblSubject(idSubject),
	idClass int  foreign key references tblClasses(idClass),
	score float,
	typeOfScore varchar(30),
	numberOfExams int,
	description nvarchar(250),
	modifiedDate datetime default(getdate())
)
go

CREATE TABLE scoreTemp (
	idScore int identity(1,1) primary key,
	rollNumber varchar(20),
	firstName nvarchar(30),
	lastName nvarchar(30),
	subjectName nvarchar(90),
	className nvarchar(64),
	score float,
	typeOfScore varchar(30),
	numberOfExams int,
	description nvarchar(250),
	modifiedDate datetime default(getdate())
); 

create table tblRoom (
	idRoom int identity(1,1) primary key,
	roomName varchar(50) unique,
	modifiedDate datetime default(getdate())  
)
go

create table tblTimetable (
	idTimetable int identity(1,1) primary key,
	idClass int foreign key references tblClasses(idClass),
	idSubject int foreign key references tblSubject(idSubject),
	startTime time,
	endTime time,
	date nvarchar(90),
	idRoom int foreign key references tblRoom(idRoom),
	idTeacher int foreign key references tblTeacher(idTeacher),
	description nvarchar(250),
	modifiedDate datetime default(getdate())
)
go

create table tblTimetableTemp (
	className nvarchar(64),
	subjectName nvarchar(90),
	startTime time,
	endTime time,
	date nvarchar(90),
	roomName varchar(50),
	firstName nvarchar(30),
	description nvarchar(250),
	modifiedDate datetime default(getdate())
)
go

create table tblPermission(
	idPermission int identity(1,1) primary key,
	permissionName nvarchar(50),
	modifiedDate datetime default(getdate())
)
go

create table tblUserPermission(
	idUserPermission int identity(1,1) primary key,
	idPermission int foreign key references tblPermission(idPermission),
	idUser int foreign key references tblUser(idUser),
	modifiedDate datetime default(getdate())
)
go

create table tblPermissionDetail(
	idPermissionDetail int identity(1,1) primary key,
	idPermission int foreign key references tblPermission(idPermission),
	actionName nvarchar(50),
	checkAction bit,
	modifiedDate datetime default(getdate())
)
go

--BỎ KÍCH HOẠT ALL RÀNG BUỘC VÀ KÍCH HOẠT LẠI

-- disable referential integrity
EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL' 
GO 
-- enable referential integrity again 
EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL' 
GO


--USER

--Tạo procedure getAllUser
create proc getAllUser
as
	select * from tblUser
go
exec getAllUser
go

--Tạo procedure getAllUserPer
create proc getAllUserPer
as
	select u.idUser, u.username, u.password, p.permissionName from tblUserPermission up
	join tblPermission p on up.idPermission = p.idPermission
	join tblUser u on up.idUser = u.idUser
go
exec getAllUserPer
go

--Tạo procedure getAllPer
create proc getAllPer
as
	select * from tblPermission p
go
exec getAllPer
go


--Tạo procedure getAllPerDetail
create proc getAllPerDetail
as
	select pd.idPermissionDetail, p.idPermission ,p.permissionName, pd.actionName, 
	case pd.checkAction when 1 then 'True' when 0 then 'False' end as checkAction from tblPermission p
	join tblPermissionDetail pd on p.idPermission = pd.idPermission
go
exec getAllPerDetail
go

--Tạo procedure insertUser
create proc insertUser
	@username varchar(50),
	@password varchar(50)
as
	insert into tblUser(username, password) values (@username, @password)
go
exec insertUser 'admin', 'admin'
go

--Tạo procedure insertUserPermission
create proc insertUserPermission
	@idPermission int,
	@idUser int
as
	insert into tblUserPermission(idPermission, idUser) values (@idPermission, @idUser)
go
exec insertUserPermission 1, 1
go

--Tạo procedure insertPermission
create proc insertPermission
	@permissionName nvarchar(50)
as
	insert into tblPermission(permissionName) values (@permissionName)
go
exec insertPermission 'a'
go

--Tạo procedure insertPermissionDetail
create proc insertPermissionDetail
	@idPermission int,
	@actionName nvarchar(50),
	@checkAction bit
as
	insert into tblPermissionDetail(idPermission, actionName, checkAction) values (@idPermission, @actionName, @checkAction)
go
exec insertPermissionDetail 1, 'read', 0
go

--Tạo procedure updateUser
create proc updateUser
	@idUser int,
	@username varchar(50),
	@password varchar(50)
as
	update tblUser
	set username=@username, password=@password
	where idUser=@idUser
go
exec updateUser 1, 'read', 'read'
go

--Tạo procedure updateUserPer
create proc updateUserPer
	@idUser int,
	@idPermission int
as
	update tblUserPermission
	set idPermission=@idPermission
	where idUser=@idUser
go
exec updateUserPer 1, 1
go

--Tạo procedure updatePermission
create proc updatePermission
	@idPermission int,
	@permissionName nvarchar(50)
as
	update tblPermission
	set permissionName=@permissionName
	where idPermission=@idPermission
go
exec updatePermission 1, 'read'
go

--Tạo procedure updatePermissionDetail
create proc updatePermissionDetail
	@idPermissionDetail int,
	@idPermission int,
	@actionName nvarchar(50),
	@checkAction bit
as
	update tblPermissionDetail
	set idPermission=@idPermission, actionName = @actionName, checkAction=@checkAction
	where idPermissionDetail=@idPermissionDetail
go
exec updatePermissionDetail 1, 'read', 0
go

--Tạo procedure searchUserPerByPer
create proc searchUserPerByPer
	@valueToSearch nvarchar(50)
as 
	select u.idUser, u.username, u.password, p.permissionName from tblUserPermission up
	join tblPermission p on up.idPermission = p.idPermission
	join tblUser u on up.idUser = u.idUser
	where p.permissionName LIKE '%' + @valueToSearch + '%' 
go
exec searchUserPerByPer n
go

--Tạo procedure searchUserPerByUserN
create proc searchUserPerByUserN
	@valueToSearch nvarchar(50)
as 
	select u.idUser, u.username, u.password, p.permissionName from tblUserPermission up
	join tblPermission p on up.idPermission = p.idPermission
	join tblUser u on up.idUser = u.idUser
	where username LIKE '%' + @valueToSearch + '%' 
go
exec searchUserPerByUserN n
go

--Tạo procedure searchPerDetailByPer
create proc searchPerDetailByPer
	@valueToSearch nvarchar(50)
as 
	select pd.idPermissionDetail, p.idPermission ,p.permissionName, pd.actionName, 
	case pd.checkAction when 1 then 'True' when 0 then 'False' end as checkAction from tblPermission p
	join tblPermissionDetail pd on p.idPermission = pd.idPermission
	where permissionName LIKE '%' + @valueToSearch + '%' 
go
exec searchPerDetailByPer 'b01'
go

--Tạo procedure searchPerDetailByActionN
create proc searchPerDetailByActionN
	@valueToSearch nvarchar(50)
as 
	select pd.idPermissionDetail, p.idPermission ,p.permissionName, pd.actionName, 
	case pd.checkAction when 1 then 'True' when 0 then 'False' end as checkAction from tblPermission p
	join tblPermissionDetail pd on p.idPermission = pd.idPermission
	where actionName LIKE '%' + @valueToSearch + '%' 
go
exec searchPerDetailByActionN 'b01'
go


--STUDENT

--Tạo procedure insertStudent
create proc insertStudent
	@idClass int,
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@birthDate date,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30)
as
	insert into tblStudent(idClass, rollNumber, firstName, lastName, gender, birthDate, phone, email, address) 
	values (@idClass, @rollNumber, @firstName, @lastName, @gender, @birthDate, @phone, @email, @address)
go
exec insertStudent 9, 'B01', 'Tuan', 'Nguyễn', 1, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertStudent 9, 'B02', 'Ha', 'Nguyễn', 1, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertStudent 4, 'B03', 'Thăng', 'Võ', 1, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertStudent 2, 'B04', 'Khanh', 'Dương', 1, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertStudent 5, 'B05', 'Hoai', 'Thu', 1, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertStudent 5, 'B06', 'Tú', 'Thu', 0, '1996-12-14', 19001256, 'tuan@gmail.com', 'Ha Giang'
go

--Tạo procedure insertStudent
create proc insertStudentTemp
	@idClass int,
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@birthDate date,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30)
as
	insert into tblStudent(idClass, rollNumber, firstName, lastName, gender, birthDate, phone, email, address) 
	values (@idClass, @rollNumber, @firstName, @lastName, @gender, @birthDate, @phone, @email, @address)
go

--Tạo procedure tblStudentTemp
create proc insertStudentTemp
	@className nvarchar(64),
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@birthDate date,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30)
as
	insert into studentTemp(className, rollNumber, firstName, lastName, gender, birthDate, phone, email, address) 
	values (@className, @rollNumber, @firstName, @lastName, @gender, @birthDate, @phone, @email, @address)
go

--Tạo procedure updateStudent
create proc updateStudent
	@idStudent int,
	@idClass int,
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@birthDate date,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30)
as
	update tblStudent
	set idClass=@idClass, rollNumber = @rollNumber, firstName=@firstName, lastName=@lastName, gender=@gender, birthDate=@birthDate, phone=@phone, email=@email, address=@address
	where idStudent=@idStudent
go
exec updateStudent 2, 1, 'B08', 'Dung', 'Nguyễn', 1, '1996-1-5', 19004654, 'dung@gmail.com', 'Sài Gòn'   
go

--Tạo procedure select tblStudent
create proc readAllStudent
as 
	select s.idStudent, s.rollNumber, s.idClass, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
go
exec readAllStudent
go

--Tạo procedure select studentTemp
create proc readAllStudentTemp
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
go
exec readAllStudentTemp
go

--Tạo procedure searchStudentByRollN
create proc searchStudentByRollN
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where s.rollNumber LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByRollN 'b01'
go

--Tạo procedure searchStudentByName
create proc searchStudentByName
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where CONCAT(s.firstName, s.lastName) LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByName 'thang'
go

--Tạo procedure searchStudentByClass
create proc searchStudentByClass
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where cl.className LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByClass '03'
go

--Tạo procedure searchStudentByPhone
create proc searchStudentByPhone
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where s.phone LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByPhone '0'
go

--Tạo procedure searchStudentByEmail
create proc searchStudentByEmail
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where s.email LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByEmail 'g'
go

--Tạo procedure searchStudentByAddress
create proc searchStudentByAddress
	@valueToSearch nvarchar(50)
as 
	select s.idStudent, s.rollNumber, cl.className, s.firstName, s.lastName, 
	case s.gender when 1 then 'Male' when 0 then 'Female' end as gender, s.birthDate, s.phone,
	s.email, s.address from tblStudent s
	join tblClasses cl on cl.idClass = s.idClass
	where s.address LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentByAddress 'g'
go

--Tạo procedure searchStudentTempByRollN
create proc searchStudentTempByRollN
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where rollNumber LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByRollN 'b01'
go

--Tạo procedure searchStudentTempByName
create proc searchStudentTempByName
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByName 'thang'
go

--Tạo procedure searchStudentTempByClass
create proc searchStudentTempByClass
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByClass '03'
go

--Tạo procedure searchStudentTempByPhone
create proc searchStudentTempByPhone
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where phone LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByPhone '0'
go

--Tạo procedure searchStudentTempByEmail
create proc searchStudentTempByEmail
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where email LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByEmail 'g'
go

--Tạo procedure searchStudentTempByAddress
create proc searchStudentTempByAddress
	@valueToSearch nvarchar(50)
as 
	select idStudent, rollNumber, className, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, birthDate, phone,
	email, address from studentTemp
	where address LIKE '%' + @valueToSearch + '%' 
go
exec searchStudentTempByAddress 'g'
go

--delete student
delete from tblStudent where rollNumber = 'b01'
go


--Subject

--Tạo procedure getALLSubject
create proc getAllSubject
as
	select * from tblSubject
go
exec getAllSubject
go


--Tạo procedure insertSubject
create proc insertSubject
	@subjectName nvarchar(90),
	@fee float
as
	insert into tblSubject(subjectName, fee) values (@subjectName, @fee)
go
exec insertSubject 'Java core 1', 5000000 
exec insertSubject 'Java core 2', 3000000 
exec insertSubject 'HTML', 2000000 
exec insertSubject 'CSS', 1000000 
exec insertSubject 'Java desktop', 8000000 
go

--Tạo procedure searchSubjectByName
create proc searchSubjectByName
	@valueToSearch nvarchar(50)
as 
	select idSubject, subjectName, fee from tblSubject
	where subjectName LIKE '%' + @valueToSearch + '%' 
go
exec searchSubjectByName 'j'
go

--Tạo procedure searchSubjectByFee
create proc searchSubjectByFee
	@valueToSearch nvarchar(50)
as 
	select idSubject, subjectName, fee from tblSubject
	where fee LIKE '%' + @valueToSearch + '%' 
go
exec searchSubjectByFee 3
go

--Tạo procedure updateSubject
create proc updateSubject
	@idSubject int,
	@subjectName nvarchar(90),
	@fee float
as
	update tblSubject
	set subjectName=@subjectName, fee=@fee
	where idSubject=@idSubject
go
exec updateSubject 1, 'ruby', 50000 
go

--delete subject
delete from tblSubject where idSubject = 2
go


--SCORE

--Tạo procedure readAll score
create proc readAllScore
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent = st.idStudent
	join tblSubject s on sc.idSubject = s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
go
exec readAllScore
go

--Tạo procedure readAll score temp
create proc readAllScoreTemp
as 
	select * from scoreTemp
go
exec readAllScoreTemp
go

--Tạo procedure insertScore
create proc insertScore
	@idStudent int,
	@idSubject int,
	@idClass int,
	@score float,
	@typeOfScore varchar(30),
	@numberOfExams int,
	@description nvarchar(250)
as
	insert into tblScore(idStudent, idSubject, idClass, score, typeOfScore, numberOfExams, description) 
	values (@idStudent, @idSubject, @idClass, @score, @typeOfScore, @numberOfExams, @description)
go
exec insertScore 18, 1, 2, 6.5, 'theory', 1, 'TB'
exec insertScore 19, 2, 3, 9, 'theory', 1, 'G'
exec insertScore 22, 3, 4, 7, 'theory', 1, 'K'
exec insertScore 23, 4, 6, 3, 'theory', 1, 'Y'
go

--Tạo procedure insertScoreTemp
create proc insertScoreTemp
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@subjectName nvarchar(90),
	@className nvarchar(64),
	@score float,
	@typeOfScore varchar(30),
	@numberOfExams int,
	@description nvarchar(250)
as
	insert into scoreTemp(rollNumber, firstName, lastName, subjectName, className, score, typeOfScore, numberOfExams, description) 
	values (@rollNumber, @firstName, @lastName, @subjectName, @className, @score, @typeOfScore, @numberOfExams, @description)
go

--Tạo procedure searchScoreByRollN
create proc searchScoreByRollN
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where rollNumber LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreByRollN a
go

--Tạo procedure searchScoreByName
create proc searchScoreByName
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreByName m
go

--Tạo procedure searchScoreByDesc
create proc searchScoreByDesc
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where description LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreByDesc k
go

--Tạo procedure searchScoreByScore
create proc searchScoreByScore
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where score LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreByScore 6
go

--Tạo procedure searchScoreBySubjectN
create proc searchScoreBySubjectN
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where subjectName LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreBySubjectN a
go

--Tạo procedure searchScoreByClassN
create proc searchScoreByClassN
	@valueToSearch nvarchar(50)
as 
	select sc.idScore, st.rollNumber, st.firstName, st.lastName, s.subjectName, cl.className, sc.score, sc.typeOfScore,
	sc.numberOfExams, sc.description from tblScore sc
	join tblStudent st on sc.idStudent=st.idStudent
	join tblSubject s on sc.idSubject=s.idSubject
	join tblClasses cl on sc.idClass = cl.idClass
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreByClassN j
go

------Tạo procedure searchScoreTempByRollN
create proc searchScoreTempByRollN
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where rollNumber LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempByRollN a
go

--Tạo procedure searchScoreTempByName
create proc searchScoreTempByName
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempByName m
go

--Tạo procedure searchScoreTempByDesc
create proc searchScoreTempByDesc
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where description LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempByDesc k
go

--Tạo procedure searchScoreTempByScore
create proc searchScoreTempByScore
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where score LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempByScore 6
go

--Tạo procedure searchScoreTempBySubjectN
create proc searchScoreTempBySubjectN
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where subjectName LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempBySubjectN a
go

--Tạo procedure searchScoreTempByClassN
create proc searchScoreTempByClassN
	@valueToSearch nvarchar(50)
as 
	select idScore, rollNumber, firstName, lastName, subjectName, className, score, typeOfScore,
	numberOfExams, description from scoreTemp
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchScoreTempByClassN j
go

--Tạo procedure updateScore
create proc updateScore
	@idScore int,
	@idStudent int,
	@idSubject int,
	@idClass int,
	@score float,
	@typeOfScore varchar(30),
	@numberOfExams int,
	@description nvarchar(250)
as
	update tblScore
	set idStudent=@idStudent, idSubject=@idSubject, idClass=@idClass, score=@score, 
	typeOfScore=@typeOfScore, numberOfExams=@numberOfExams, description=@description
	where idScore=@idScore
go
exec updateScore 1, 18, 1, 2, 2, 'theory', 2, 'TB'
go

--Tạo procedure delete score
delete from tblScore where idScore = 1

--Tạo procedure score by student
create proc avgScoreByStudents
as 
	select AVG(sc.score) as score, s.rollNumber from tblScore sc
	inner join tblStudent s on sc.idStudent = s.idStudent
	group by s.rollNumber
go
exec avgScoreByStudents
go

--CLASSES

--Tạo procedure readAll classes
create proc readAllClasses
as 
	select idClass, className, numberOfStudents from tblClasses
go
exec readAllClasses
go

--Tạo procedure readAll tblClassTemp
create proc readAllClassTemp
as 
	select idClass, className, numberOfStudents from classesTemp
go
exec readAllClassTemp
go

--Tạo procedure insertClass
create proc insertClass
	@className nvarchar(64),
	@numberOfStudents int
as
	insert into tblClasses(className, numberOfStudents) 
	values (@className, @numberOfStudents)
go
exec insertClass 'LAB 02', 30
exec insertClass 'LAB 01', 30
exec insertClass 'LAB 03', 30
exec insertClass 'LAB 04', 30
exec insertClass 'LAB 05', 30
go

--Tạo procedure insert tblClassTemp
create proc insertClassTemp
	@className nvarchar(64),
	@numberOfStudents int
as
	insert into classesTemp(className, numberOfStudents) 
	values (@className, @numberOfStudents)
go

--Tạo procedure searchClassByName
create proc searchClassByName
	@valueToSearch nvarchar(50)
as 
	select idClass, className, numberOfStudents from tblClasses
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchClassByName 'c'
go

--Tạo procedure searchClassByNOStudents
create proc searchClassByNOStudents
	@valueToSearch nvarchar(50)
as 
	select idClass, className, numberOfStudents from tblClasses
	where numberOfStudents LIKE '%' + @valueToSearch + '%' 
go
exec searchClassByNOStudents 30
go

--Tạo procedure searchTempClassByName
create proc searchTempClassByName
	@valueToSearch nvarchar(50)
as 
	select idClass, className, numberOfStudents from classesTemp
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchTempClassByName 'c'
go

--Tạo procedure searchTempClassByNOStudents
create proc searchTempClassByNOStudents
	@valueToSearch nvarchar(50)
as 
	select idClass, className, numberOfStudents from classesTemp
	where numberOfStudents LIKE '%' + @valueToSearch + '%' 
go
exec searchTempClassByNOStudents 30
go

--Tạo procedure updateClass
create proc updateClass
	@idClass int,
	@className nvarchar(64),
	@numberOfStudents int
as
	update tblClasses
	set className = @className, numberOfStudents = @numberOfStudents
	where idClass=@idClass
go
exec updateClass 1, 'C05', 25
go

--delete class
delete from tblStudent where idClass = 2
delete from tblClasses where idClass = 2
go


--TIMETABLE

--Tạo procedure readAll timetable
create proc readAllTimetable
as 
	select tt.idTimetable, cl.className, s.subjectName, FORMAT(CAST(tt.startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(tt.endTime as datetime), 'hh:mm tt') 'endTime',
	tt.date, r.roomName as room, t.firstName as teacher, tt.description, tt.modifiedDate from tblTimetable tt
	join tblSubject s on tt.idSubject=s.idSubject
	join tblClasses cl on tt.idClass=cl.idClass
	join tblRoom r on tt.idRoom=r.idRoom
	join tblTeacher t on tt.idTeacher=t.idTeacher
go
exec readAllTimetable
go

--Tạo procedure readAll timetable
create proc readAllTimetableTemp
as 
	select className, subjectName, FORMAT(CAST(startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(endTime as datetime), 'hh:mm tt') 'endTime',
	date, roomName, firstName, description, modifiedDate from tblTimetableTemp

go
exec readAllTimetableTemp
go

--Tạo procedure insertTimetable
create proc insertTimetable
	@idClass int,
	@idSubject int,
	@startTime time,
	@endTime time,
	@date nvarchar(90),
	@idRoom int,
	@idTeacher int,
	@description nvarchar(250)
as
	insert into tblTimetable(idClass, idSubject, startTime, endTime, date, idRoom, idTeacher, description) 
	values (@idClass, @idSubject, @startTime, @endTime, @date, @idRoom, @idTeacher, @description)
go
exec insertTimetable 30, 2, '10:00 AM', '12:00', 'Monday-Sunday', 1, 2, 'aaaa'
exec insertTimetable 2, 3, '14:00', '17:00', 'Monday-Sunday', 2, 3, 'aaaa'
exec insertTimetable 3, 4, '10:00', '12:00', 'Monday-Sunday', 3, 4, 'aaaa'
exec insertTimetable 4, 1, '10:00', '12:00', 'Monday-Sunday', 1, 4, 'aaaa'
go

--Tạo procedure insertTimetableTemp
create proc insertTimetableTemp
	@className nvarchar(64),
	@subjectName nvarchar(90),
	@startTime time,
	@endTime time,
	@date nvarchar(90),
	@roomName varchar(50),
	@firstName nvarchar(30),
	@description nvarchar(250)
as
	insert into tblTimetableTemp(className, subjectName, startTime, endTime, date, roomName, firstName, description) 
	values (@className, @subjectName, @startTime, @endTime, @date, @roomName, @firstName, @description)
go

--Tạo procedure searchTimetableByClass
create proc searchTimetableByClass
	@valueToSearch nvarchar(50)
as 
	select tt.idTimetable, cl.className, s.subjectName,  FORMAT(CAST(tt.startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(tt.endTime as datetime), 'hh:mm tt') 'endTime',
	tt.date, r.roomName, t.firstName as teacher, tt.description, tt.modifiedDate from tblTimetable tt
	join tblSubject s on tt.idSubject=s.idSubject
	join tblClasses cl on tt.idClass=cl.idClass
	join tblRoom r on tt.idRoom=r.idRoom
	join tblTeacher t on tt.idTeacher=t.idTeacher
	where cl.className LIKE '%' + @valueToSearch + '%' 
go
exec searchTimetableByClass 6
go

--Tạo procedure searchTimetableByTeacher
create proc searchTimetableByTeacher
	@valueToSearch nvarchar(50)
as 
	select tt.idTimetable, cl.className, s.subjectName,  FORMAT(CAST(tt.startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(tt.endTime as datetime), 'hh:mm tt') 'endTime',
	tt.date, r.roomName, t.firstName as teacher, tt.description, tt.modifiedDate from tblTimetable tt
	join tblSubject s on tt.idSubject=s.idSubject
	join tblClasses cl on tt.idClass=cl.idClass
	join tblRoom r on tt.idRoom=r.idRoom
	join tblTeacher t on tt.idTeacher=t.idTeacher
	where t.firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchTimetableByTeacher 'k'
go

--Tạo procedure searchTimetableByClassTemp
create proc searchTimetableByClassTemp
	@valueToSearch nvarchar(50)
as 
	select className, subjectName,  FORMAT(CAST(startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(endTime as datetime), 'hh:mm tt') 'endTime',
	date, roomName, firstName, description, modifiedDate from tblTimetableTemp
	where className LIKE '%' + @valueToSearch + '%' 
go
exec searchTimetableByClassTemp 6
go

--Tạo procedure searchTimetableByTeacherTemp
create proc searchTimetableByTeacherTemp
	@valueToSearch nvarchar(50)
as 
	select className, subjectName,  FORMAT(CAST(startTime as datetime), 'hh:mm tt') 'startTime', FORMAT(CAST(endTime as datetime), 'hh:mm tt') 'endTime',
	date, roomName, firstName, description, modifiedDate from tblTimetableTemp
	where firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchTimetableByTeacherTemp 'k'
go

--Tạo procedure updateTimetable
create proc updateTimetable
	@idTimetable int,
	@idClass int,
	@idSubject int,
	@startTime time,
	@endTime time,
	@date nvarchar(90),
	@idRoom int,
	@idTeacher int,
	@description nvarchar(250)
as
	update tblTimetable
	set idClass=@idClass, idSubject=@idSubject, startTime=@startTime, endTime=@endTime, date=@date, idRoom=@idRoom, idTeacher=@idTeacher, description=@description
	where idTimetable=@idTimetable
go
exec updateTimetable 1, 2, 1, '10:00', '15:00', 'Monday-Sunday', 3, 2, 'aaaa'
go

--delete timetable
delete from tblTimetable where idTimetable = 2
go

--ROOM

--Tạo procedure readAll room
create proc readAllRoom
as 
	select idRoom, roomName from tblRoom
go
exec readAllRoom
go

--Tạo procedure insertRoom
create proc insertRoom
	@roomName varchar(50)
as
	insert into tblRoom(roomName) 
	values (@roomName)
go
exec insertRoom 'A 02'
exec insertRoom 'F 01'
exec insertRoom 'B 03'
exec insertRoom 'Y 04'
exec insertRoom 'N 05'
go

--Tạo procedure searchRoom
create proc searchRoom
	@valueToSearch nvarchar(50)
as 
	select idRoom, roomName from tblRoom
	where roomName LIKE '%' + @valueToSearch + '%' 
go
exec searchRoom 'n'
go

--Tạo procedure updateRoom
create proc updateRoom
	@idRoom int,
	@roomName varchar(50)
as
	update tblRoom
	set roomName = @roomName
	where idRoom = @idRoom
go
exec updateRoom 3, 'B 08'
go

--delete room
delete from tblRoom where idRoom = 2
go


--TEACHER

--Tạo procedure select tblTeacher
create proc readAllTeacher
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
go
exec readAllTeacher
go

--Tạo procedure insertTeacher
create proc insertTeacher
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30) 
as
	insert into tblTeacher(rollNumber, firstName, lastName, gender, phone, email, address) 
	values (@rollNumber, @firstName, @lastName, @gender, @phone, @email, @address)
go
exec insertTeacher 'A02', 'Tuan', 'Nguyễn', 1, 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertTeacher 'A03', 'Ha', 'Nguyễn', 1, 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertTeacher 'b05', 'Thăng', 'Võ', 1, 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertTeacher 'c06', 'Khanh', 'Dương', 1, 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertTeacher 'b02', 'Hoai', 'Thu', 1, 19001256, 'tuan@gmail.com', 'Ha Giang'
exec insertTeacher 'd01', 'Tú', 'Thu', 0, 19001256, 'tuan@gmail.com', 'Ha Giang'
go

--Tạo procedure update tblTeacher
create proc updateTeacher
	@idTeacher int,
	@rollNumber varchar(20),
	@firstName nvarchar(30),
	@lastName nvarchar(30),
	@gender bit,
	@phone varchar(15),
	@email varchar(70),
	@address nvarchar(30) 
as 
	update tblTeacher
	set rollNumber = @rollNumber, firstName = @firstName, lastName = @lastName, gender = @gender, phone = @phone, email = @email,
	address = @address
	where idTeacher = @idTeacher
go
exec updateTeacher 1, 'f03', 'a', 'a', 0, 19001256, 'a@gmail.com', 'a'
go

--Tạo procedure searchTeacherByRollN
create proc searchTeacherByRollN
	@valueToSearch nvarchar(50)
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
	where rollNumber LIKE '%' + @valueToSearch + '%' 
go
exec searchTeacherByRollN n
go

--Tạo procedure searchTeacherByName
create proc searchTeacherByName
	@valueToSearch nvarchar(50)
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
	where firstName LIKE '%' + @valueToSearch + '%' 
go
exec searchTeacherByName n
go

--Tạo procedure searchTeacherByPhone
create proc searchTeacherByPhone
	@valueToSearch nvarchar(50)
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
	where phone LIKE '%' + @valueToSearch + '%' 
go
exec searchTeacherByPhone 1
go

--Tạo procedure searchTeacherByEmail
create proc searchTeacherByEmail
	@valueToSearch nvarchar(50)
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
	where email LIKE '%' + @valueToSearch + '%' 
go
exec searchTeacherByEmail a
go

--Tạo procedure searchTeacherByAddress
create proc searchTeacherByAddress
	@valueToSearch nvarchar(50)
as 
	select idTeacher, rollNumber, firstName, lastName, 
	case gender when 1 then 'Male' when 0 then 'Female' end as gender, phone,
	email, address from tblTeacher
	where address LIKE '%' + @valueToSearch + '%' 
go
exec searchTeacherByAddress n
go

--drop bảng
drop table tblUser
go
drop table tblStudent
go
drop table tblClasses
go
drop table tblSubject
go
drop table tblScore
go
drop table tblTimetable
go
drop table tblTeacher
go
drop table tblRoom
go
drop table tblUserPermission
go
drop table tblPermission
go
drop table tblPermissionDetail
go

--select bảng
select * from tblUser
go
select * from tblStudent
select count (distinct rollNumber) as totalStudents from tblStudent where gender=1
go
select * from tblClasses
go
select * from tblSubject
select COUNT(*) as totalCourse from tblSubject
go
select * from tblScore
go
select * from tblTimetable
go