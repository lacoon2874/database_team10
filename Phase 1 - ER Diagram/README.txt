지역병원(소규모 병원)환자들을 위한 의료 대시보드 웹사이트

1. 서비스 내용
-병원 서비스 정보, 의사 스케줄, 입원실 현황, 대기인원, 개인 의료차트 등 지역병원에 방문하는 환자들에게 한눈에 알아보기 쉬운 의료 대시보드를 제공한다.


2. ER 다이어그램 Description

[1] Entity & Attribute Description

1) HOSPITAL : 병원 정보를 담고 있는 entity
- Name: 병원 이름을 나타내는 attribute로 같은 병원 이름과 위치를 동시에 가질 수 없으므로 Key attribute
- Location : 병원 위치를 나타내는 attribute로 같은 위치에는 하나의 병원만 존재할 수 있으므로 Key attribute
- Map : 위치에 대한 구글맵 연동을 하는 attribute로 위치로부터 나오는 Derived attribute
- Medical_equipment : 병원이 가지고 있는 의료 기기
- Treatment_subject : 병원이 치료하는 과목(부서)
- Check_covid19 : 병원의 코로나검사(신속항원검사, PCR검사) 수행 여부
- Phone_number : 해당 병원의 전화번호
- Consultation_hours : 해당 병원의 진료시간

2) DOCTER : 의사 정보 및 스케줄을 담고 있는 entity
- Identification_number : 병원 내에서 의사를 식별하기 위해 부여하는 번호를 나타내는 attribute로 각자 다른 번호가 부여되므로 Key_attribute
- Treatment_subject : 의사가 담당하는 치료 과목(ex. 내과,외과)
- Schedule : 의사의 예약 및 치료 스케줄 정보
- Career : 의사의 경력
- Name : 이름을 나타내는 attribute로 First_name과 Last_name이 합쳐진 Composite attribute

3) PATIENT : 환자 정보를 담고 있는 entity
- SSN : 주민등록번호를 나타내는 attribute로 같은 주민등록번호를 가진 사람은 없으므로 Key attribute
- Birth_date : 생년월일
- Age : 나이를 나타내는 attribute로 생년월일로부터 계산되므로 Derived attribute
- Body_Info: 개인 신체 정보를 나타내는 attribute로 Height와 Weight이 합쳐진 Composite attribute
- Height : 키
- Weight : 몸무게
- Address : 주소
- Id : 로그인 시 입력하는 환자의 Id
- Password : 로그인 시 입력하는 환자의 Password
- Name : 환자의 이름을 나타내는 attribute로 First_name과 Last_name이 합쳐진 Composite attribute
- Sex : 환자의 성별
- Phone_num : 환자의 전화번호

4) DEPARTMENT : 병원의 부서(과목) 정보를 담고 있는 entity로 외과, 내과, 정형외과 등을 나타냄
- Department_code : 부서코드를 나타내는 attribute로 서로 다른 부서코드로 부서를 식별하므로 Key attribute
- Type : 부서 종류(부서명) (ex. 외과, 내과 등)


5) WAITING_LIST : 병원의 현재 진료 대기현황(대기인원, 진료상태)을 담고있는 entity
- Time : 진료 대기상황을 업데이트한 시간을 나타내는 attribute로 같은 시간을 가질 수 없으므로 Key attribute
- Num_of_waiting : 업데이트한 시간의 대기 인원수 현황
- Treatment_state : 현재 의사의 진료상태를 나타내는 attribute(ex. 진료중)

6) WARD : 병원의 입원실 현황을 담고 있는 entity(입원실이 없는 소형 병원의 경우 링거실)
- Time : 입원실(링거실) 현황을 업데이트한 시간을 나타내는 attribute로 같은 시간을 가질 수 없으므로 Key attribute
- Ward_type : 입원실(링거실) 종류 (ex. VIP실, 1인실, 2인실 등)
- Num_of_empty_bed : 업데이트한 시간에 남아있는 빈 침대의 수
- Price : 입원실 비용

7) RECORD : 환자의 개인 진료차트 및 내역을 담고 있는 weak entity로 PATIENT와 HOSPITAL이 없으면 존재할 수 없으므로 Weak entity
- Datetime : 환자의 진료 날짜 및 시간을 나타내는 attribute로 weak entity를 식별해주므로 Partial key
- History : 환자의 진료 내역(병원, 부서, 의사)
- Disease_entity : 과거 진료시 환자의 병명 (ex.독감)
- Symptom : 과거 진료시 환자의 증상 (ex.발열, 인후통 등)



[2] Relationship Description

1) SERVICE
	- 병원에서 부서를 서비스(치료)해줌
	- 모든 병원은 하나 이상의 부서를 가져야하며 모든 부서는 병원 내에 존재해야 함 (1:N / Total participation)
2) HAS
	- 병원은 입원실(링거실)을 가짐
	- 입원실(링거실)이 없는 병원이 존재할 수 있으며 모든 입원실(링거실)은 병원내에 있어야 함 (Partial participation of HOSPITAL , Total participation of WARD)
	- 하나의 병원은 여러개의 입원실(링거실)을 가질 수 있음(1:N)
3) WORK
	- 의사는 각 부서에서 일함
	- 모든 부서는 의사가 있어야하며 모든 의사는 부서에서 일을 해야함 (Total participation)
	- 1개의 부서에는 여러명의 의사가 있으며 의사는 1가지의 부서에서 일함 (1:N)
4) IN_CHARGE
	- 의사마다 대기인원을 담당하고있음(가지고 있음)
	- 모든 의사가 대기인원이 있는 것이 아니고 모든 대기 현황에서 담당의사를 배정할 필요 없음(Partial participation)
	- 한명의 의사가 여러명의 대기인원을 가질 수 있으며 담당의사를 배정할 필요 없으므로 대기현황을 여러명의 의사가 담당할 수 있음(M:N)
5) CONTROLS
	- 병원에서 환자의 정보를 관리
	- 모든 병원이 환자를 가져야하는 것이 아니며 환자가 모두 병원을 가져야하는 것이 아님(Partial participation)
	- 하나의 병원에서는 여러 환자의 정보를 가지고 있고, 한 환자가 여러 병원에서 치료를 받을 수 있음 (M:N)
6) DEPENDENTS_OF
	- 환자의 기록은 환자가 없으면 존재할 수 없는 weak entity로 PATIENT와 RECORD가 가지는 식별관계
	- 환자는 기록을 가지지 않아도 되지만 기록은 항상 환자가 존재해야함(Partial participation of PATIENT , Total participation of RECORD)
	- 환자는 여러 번 치료를 받아 여러개의 기록을 가질 수 있으며 하나의 치료 기록은 하나의 환자 정보에만 해당한다(1:N)
7) PROVIDE_INFO
	- 병원은 병원 정보, 간단한 진료 정보를 제공
	- 기록은 병원이 없다면 존재할 수 없으므로 식별관계에 해당함
	- 병원은 기록을 제공하지 않을 수 있지만 기록은 항상 병원의 정보가 있어야 함(Partial participation of HOSPITAL , Total participation of RECORD)
	- 병원은 여러 번 정보를 제공할 수 있고 기록은 여러 병원에서 정보를 받을 수 있음(M:N)