1. Phase3_query.java (Phase2 QUERY)
홀수 QUERY


짝수 QUERY
- 2번 (Type1) : 성별이 O인 환자의 정보(ID, 이름, 성, 비밀번호, Ssn,  성별, 생년월일, 전화번호, 주소, 체중, 키) 조회   /  M 또는  F입력
- 10번 (Type5) : 레코드가 존재하지 않는 환자 중 생일이 O월인 환자의 정보(ID, 이름, 성, 비밀번호, Ssn,  성별, 생년월일, 전화번호, 주소, 체중, 키) 조회   /   1~12 중에서 입력
	*수정 : WHERE절에 환자의 생월(생일의 월) 조건 추가
- 12번 (Type6) : 현재 대기 인원이 O명 이하인 의사들의 정보(의사ID번호, 성, 이름, 진료과목) 조회
	*수정 : 조회할 컬럼을 수정(* -> ID_NUMBER, Lname, Fname, Treatment_subject)
- 18번 (Type9) : 진료과목에 OO부서가 있는 병원의 빈 병실침대 수를 내림차순으로 조회   /   병원 부서명 입력 (ex.내과)
	*수정 : WHERE절에 병원의 진료과목 조건 추가
- 20번 (Type10) : OO과목을 진료하는 병원 중 OO지역에 위치한 병원 조회    /   병원 부서명, 병원 주소 입력 (ex.내과, 달서구)


2. team10_dml.java (DML)
INSERT
- addPatient : 병원에 환자 정보 연결(환자 정보가 database에 없을 경우 추가 후 연결) / 병원에서 추가하므로 병원 id가 인자로 요구됨 / 입력을 통해 환자 정보 추가
- addRecord : 환자의 치료 기록 추가 / 병원에서 추가하므로 병원 id와 대상 환자의 ssn이 인자로 요구됨 / 입력을 통해 치료 기록 추가
- addWard : 병원에 새로운 병실 추가 / 병원에서 추가하므로 병원 id가 인자로 요구됨 / 입력을 통해 병실 추가
- addDepartment : 병원에 새로운 부서 추가 / 병원에서 추가하므로 병원 id가 인자로 요구됨 / 입력을 통해 부서 추가
- addDoctor : 부서에 새로운 의사 추가 / 병원에서 추가하므로 병원 id와 소속될 부서 code가 인자로 요구됨 / 입력을 통해 의사 정보 추가 / 의사에 대해 대기 인원 정보가 존재해야 하므로 addWaitingList를 호출하여 대기정보를 바로 추가
- addWaitingList : 의사의 대기 인원 정보 추가 / 의사 하나에 하나만 연결되므로 의사 id가 인자로 요구됨 / 입력을 통해 대기 인원 정보 추가

DELETE
- removePatient : 병원에서 환자 정보 연결 제거(database에 환자 정보는 유지) / 병원에서 제거하므로 병원 id가 인자로 요구됨 / 제거할 환자 ssn을 입력으로 받음
- removeWard : 병원의 병실 정보 제거 / 병원에서 제거하므로 병원 id가 인자로 요구됨 / 제거할 병실 type을 입력으로 받음
- removeDepartment : 병원의 부서 정보 제거 / 병원에서 제거하므로 병원 id가 인자로 요구됨 / 제거할 부서 코드를 입력으로 받음 / 해당 병원 id와 부서 code를 통해 소속된 의사 정보를 모두 제거(removeDoctor 호출)한 후에 부서 정보를 제거함
- removeDoctor : 부서의 의사 정보 제거 / 병원에서 제거하므로 병원 id와 소속된 부서 code가 인자로 요구됨 / 부서 제거를 통해 제거할 경우 해당 의사 id를 인자로 넘겨주고, 의사 개별로 제거할 경우 id를 입력을 통해 받음 / 의사 정보를 제거하기 전에 removeWaitingList를 호출하여 대기 인원 정보를 먼저 삭제하고 의사 정보를 제거함
- removeWaitingList : 의사의 대기 인원 정보 제거 / 의사 하나에 하나만 연결되므로 의사 id가 인자로 요구됨

UPDATE
- changePatientPassword : 환자 계정 비밀번호 변경 / 환자가 변경하므로 환자 ssn가 인자로 요구됨 / 변경할 비밀번호를 입력으로 받음
- changePatientPhoneNum : 환자 전화번호 변경 / 환자가 변경하므로 환자 ssn가 인자로 요구됨 / 변경할 전화번호를 입력으로 받음
- changePatientAddress : 환자 주소 변경 / 환자가 변경하므로 환자 ssn가 인자로 요구됨 / 변경할 주소를 입력으로 받음
- changeWardPrice : 병원의 병실 가격 변경 / 병원에서 변경하므로 병원 id와 변경할 병실의 type이 인자로 요구됨 / 변경할 가격을 입력으로 받음
- changeWardEmptyBedNum : 병원의 병실 남은 개수 변경 / 병원에서 변경하므로 병원 id와 변경할 병실의 type이 인자로 요구됨 / 변경할 개수을 입력으로 받음
- changeDoctorSchedule : 의사 스케줄 변경 / 의사가 변경하므로 본인 id가 인자로 요구됨 / 변경할 스케줄을 입력으로 받음
- changeWaitingListNum : 의사 대기 인원 수 변경 / 의사 기준으로 변경하므로 의사 id가 인자로 요구됨 / 변경할 수를 입력으로 받음

SELECT
** selectFunction : data(선택할 열 또는 데이터), table_name(테이블 이름), condition(where, order by 등 조건), param(condition에 존재하는 파라미터들의 순서), paramValid(like와 같이 getColumnType이 안되는 경우를 판단) -> 5개의 인자와 입력을 통해 query문을 동작하여 결과를 출력하는 함수
- getHospitalList : ㅇㅇ지역에 있는 병원의 '이름, 위치, 진료 과목' 정보 출력
- getHospitalInformation : ㅇㅇ병원의 '이름, 위치, 전화번호, 진료 과목, 영업 시간, 의료 기기, 코로나 검사 여부' 정보 출력
- getWard : ㅇㅇ병원의 병실들의 '변경시간, 가격, 병실 유형, 빈 침대 수' 정보 출력
- getDoctorInformation : ㅇㅇ의사의 '성, 이름, 스케줄, 경력, 진료 상태, 대기 인원 수' 정보 출력
- getPatientInformation : ㅇㅇ환자의 '성, 이름, 주민번호, 성별, 생년월일, 전화번호, 주소, 체중, 키' 정보 출력

3. Phase2 SQL파일의 수정된 부분
(1) Phase 2-1.sql
CREATE HOSPITAL : Treatement_subject컬럼명을 Treatment_subject로 수정
CREATE DEPARTMENT : Department_code의 data type을 VARCHAR(2)로 수정  /  Primary key를 (Hid,Department_code)로 수정
CREATE DOCTOR : Department_code의 data type을 VARCHAR(2)로 수정
ALTER DOCTOR : (Hid,Department_code)가 DEPARTMENT의 (Hid,Department_code)참조
CREATE WAITING_LIST : Primary key를 (Doctor_id_num, Time)으로 수정
CREATE WARD : Primary key를 (Hid, Ward_type)으로 수정

(2) Phase 2-2.sql
DEPARTMENT : Department_code를 2자리 숫자로 수정 (내과:01/외과:02/정형외과:03/소아청소년과:04/산부인과:05/이비인후과:06/안과:07/신경외과:08/영상의학과:09/재활의학과:10/마취통증의학과:11/치과:12)
DOCTOR : Department_code를 2자리 숫자로 수정, Hid(병원 id)를 추가
WARD : 중복된 (Hid,Ward_type)값 수정

(3) Phase 2-3.sql
- 5번 (Type2) : DEPARTMENT의 Primary key의 수정에 따라 WHERE절에 DP.Hid=DT.Hid 조건을 추가
- 7번 (Type3) : DEPARTMENT의 Primary key의 수정에 따라 WHERE절에 DP.Hid=DT.Hid 조건을 추가
- 14번 (Type7) : DEPARTMENT의 Primary key의 수정에 따라 WHERE절에 DP.Hid=DT.Hid 조건을 추가
- 18번 (Type9) : SELECT절 집계함수를 SUM으로 수정