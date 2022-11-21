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