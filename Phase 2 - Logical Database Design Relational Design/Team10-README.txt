

Phase2-1.sql : 제약조건들을 포함한 DDL문을 통해 Table 생성함. 

1) HOSPITAL Table 생성 : Hospital_id - 병원을 식별할 PRIMARY KEY
2) DOCTER Table 생성 : Id_number - 의사를 식별할 PRIMARY KEY , FOREIGN KEY인 Department_code가 DEPARTMENT의Department_code를 참조
3) PATIENT Table 생성 : SSN - 주민등록번호를 나타내는 PRIMARY KEY, UNIQUE 로 Id
4) DEPARTMENT Table 생성 : Department_code - 부서들을 식별할 PRIMARY KEY ,FOREIGN KEY인 Hid가 HOSPITAL의Hospital_id를 참조
5) WAITING_LIST Table 생성:Time - WAITING_LIST를 시간으로 식별하기 때문에 Time이 PRIMARY KEY,FOREIGN KEY인Doctor_id_num이 DOCTOR의 Id_number를 참조
6) WARD Table 생성 : Time - WARD를 시간으로 식별하기 때문에 Time이 PRIMARY KEY,FOREIGN KEY인 Hid가 HOSPITAL의 Hospital_id를 참조
7) RECORD Table 생성 :Datetime,Patient_SSN - 시간이 중복이 될수도 있기에 환자주민번호와 묶어서 PRIMARY KEY,FOREIGN KEY인 Patient_SSN가 PATIENT의 SSN를 참조 ,FOREIGN KEY인 Hid가 HOSPITAL의 Hospital_id를 참조
8) CONTROLS Table 생성 :Hid,Patient_SSN - 병원 식별아이디가 중복이 될수도 있기에 환자주민번호와 묶어서 PRIMARY KEY,FOREIGN KEY인 Patient_SSN가 PATIENT의 SSN를 참조 ,FOREIGN KEY인 Hid가 HOSPITAL의 Hospital_id를 참조


Phase2-2.sql : DDL를 통해 생성된 table에 대한 INSERT를 실행함.
1) HOSPITAL : (Hospital_id, Name, Location, Phone_number, Treatement_subject, Consultation_hours, Medical_equipment, Check_covid19) 총 20개의 data Insert
2) DOCTER :(Id_number, Name, Treatment_subject, Department_code, Schedule, Career) 총 65개의 data Insert
3) PATIENT :(Id, Fname, Lname, Password, SSN, Sex, Birth_date, Address, Weight, Height) 총 71개의 data Insert, record가 0개인 환자 15명 ,record가 1개인 환자 30명,record가 2개인 환자 10명 ,record가 3개인 환자 10명,record가 4개인 환자 5명
4) DEPARTMENT :(Type, Department_code, Hid)총 35 개의 data Insert
5) WAITING_LIST :(Time, Num_of_waiting, Treatement_state) 총 60개의 data Insert
6) WARD :(Time, Price, Ward_type, Number_of_empty_bed, Hid) 총 30개의 data Insert
7) RECORD : (Datetime, History, Disease_entity, Symptom, Patient_SSN,Hid) 총 100개의 data Insert
8) CONTROLS:(Hid, Patient_SSN) 총 120개의 data Insert
*Data 50개 미만인 table :HOSPITAL, DEPARTMENT, WARD


Phase2-3.sql : 20개의 쿼리문들 나타냄. 

1)Type1 성이 '김'씨인 환자의 정보를 나타냄 
2)Type1 성별이 남자인 환자 정보를 나타냄
3)Type1 가격이 얼마이상인 입원실 정보를 나타냄
4)Type2 치료 기록이 있는 환자 list 출력함 : 병원+환자+record = 병원에 등록되어있고 진료기록까지 있는 환자에 대해 list 출력
5)Type2 병원 별 부서 및 의사 정보를 나타냄
6)Type3 병원별 등록되어 있는 환자수를 나타냄
7)Type3 부서별(부서 이름 기준) 의사수
8)Type4 id_number로 검색하여 id_number가 123456789인 의사와 동일한 경력(같은학교졸업)한 의사들 정보를 나타냄
9)Type4 id_number로 검색하여 id_number가 123456789인 의사와 동일한 스케줄을 가진 의사들 정보를 나타냄
10)Type5 레코드가 없는(0개) 환자의 정보를 나타냄
11)Type5 외과를 가지고 있는 병원정보를 나타냄
12)Type6 현재 대기 인원이 없는 의사들의 정보를 나타냄
13)Type7 현재 진료중인 의사 정보를 대기 인원이 적은 순으로 나타냄
14)Type7 정형외과에서 일하는 의사의 이름과 스케줄을 나타냄
15)Type8 입원실 가격이 적은 순으로 병원과 입원실 정보를 나타냄
16)Type8 대기 인원이 적은 순서대로 의사와 대기열 정보를 나타냄
17)Type9 record가 많은 순서대로 출력하고 record가 없으면 출력하지 않음
18)Type9 빈 침대 수가 많은 순서대로 출력함
19)Type10 내과를 진료하는 병원 중 환자 수가 6명 이상인 병원 이름을 나타냄
20)Type10 내과를 진료하는 병원 중 달서구에 위치한 병원이름을 나타냄







