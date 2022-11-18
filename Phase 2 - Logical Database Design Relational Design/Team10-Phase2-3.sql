-- 1. Type1 - 성이 '김'씨인 환자의 정보
SELECT * FROM patient WHERE Lname='김';

-- 2. Type1 -  성별이 남자인 환자 정보
SELECT * FROM patient WHERE Sex='M';

-- 3. Type1 -  가격이 20만원 이상인 입원실 정보
SELECT * FROM ward WHERE price>=200000;

-- 4. Type2 - 치료 기록이 있는 환자 list 출력 (병원+환자+record = 병원에 등록되어있고 진료기록까지 있는 환자에 대해 list 출력)
SELECT distinct P.* FROM hospital H, controls C, patient P, record R WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn and P.ssn=R.patient_ssn and H.hospital_id=R.hid;

-- 5. Type2 - 병원 별 부서 및 의사 정보 - 수정
SELECT * FROM hospital H, department DP, doctor DT WHERE H.hospital_id=DP.hid and DP.department_code=DT.department_code AND DP.Hid=DT.Hid;

-- 6. Type3 - 병원별 등록되어 있는 환자수
SELECT H.name, count(*) FROM hospital H, controls C, patient P WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn GROUP BY H.name;

-- 7. Type3 - 부서별(부서 이름 기준) 의사수 - 수정
SELECT DP.type, count(*) FROM department DP, doctor DT WHERE DP.department_code=DT.department_code AND DP.Hid=DT.Hid GROUP BY DP.type;

-- 8. Type4 - ㅇㅇ의사(id_number로 검색)와 동일한 경력(같은학교졸업)한 의사들 정보
SELECT * FROM doctor WHERE career=(SELECT career FROM doctor WHERE id_number='123456789');

-- 9. Type4 - ㅇㅇ의사(id_number로 검색)와 동일한 스케줄을 가진 의사들 정보
SELECT * FROM doctor WHERE schedule=(SELECT schedule FROM doctor WHERE id_number='123456789');

-- 10. Type5 - 레코드가 없는 환자의 정보
SELECT * FROM patient P WHERE NOT EXISTS(SELECT 1 FROM record R WHERE P.ssn=R.patient_ssn);

-- 11. Type5 - 외과를 가지고 있는 병원정보
SELECT * FROM hospital H WHERE EXISTS(SELECT 1 FROM department D WHERE H.hospital_id=D.hid and D.type='외과');

-- 12. Type6 - 현재 대기 인원이 없는 의사들의 정보
SELECT * FROM doctor WHERE id_number IN(SELECT doctor_id_num from waiting_list where num_of_waiting<=0);

-- 13. Type7 - 현재 진료중인 의사 정보를 대기 인원이 적은 순으로 출력
SELECT Fname, Lname, treatment_subject, schedule, num_of_waiting FROM
(SELECT * FROM doctor D, waiting_list W WHERE D.id_number=W.doctor_id_num)
WHERE treatment_state='진료중' ORDER BY num_of_waiting ASC;

-- 14. Type7 - 정형외과에서 일하는 의사의 이름과 스케줄 출력 - (수정)
SELECT * FROM
(SELECT DP.type, DT.Lname, DT.Fname, DT.schedule FROM department DP, doctor DT WHERE DP.department_code=DT.department_code and DP.Hid=DT.Hid)
WHERE type='정형외과';

-- 15. Type8 - 입원실 가격이 적은 순으로 병원+입원실 정보 출력
SELECT * FROM hospital H, ward W WHERE H.hospital_id=W.hid ORDER BY W.price ASC;

-- 16. Type8 - 대기 인원이 적은 순서대로 의사+대기열 정보 출력
SELECT * FROM doctor D, waiting_list W WHERE D.id_number=W.doctor_id_num ORDER BY num_of_waiting ASC;

-- 17. Type9 - record가 많은 순서대로 출력, record가 없으면 출력하지 않음
SELECT P.ssn, count(*) FROM patient P, record R WHERE P.ssn=R.patient_ssn GROUP BY P.ssn ORDER BY count(*) DESC;

-- 18. Type9 - 빈 침대 수가 많은 순서대로 출력
SELECT H.name, SUM(W.num_of_empty_bed) FROM hospital H, ward W WHERE H.hospital_id=W.hid GROUP BY H.name ORDER BY SUM(W.num_of_empty_bed) DESC;

-- 19. Type10 - 내과를 진료하는 병원 중 환자 수가 6명 이상인 병원 이름
(SELECT H.name FROM hospital H, controls C, patient P WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn GROUP BY H.name HAVING count(*)>=6)
INTERSECT
(SELECT H.name Hname FROM hospital H, department D WHERE H.hospital_id=D.hid and D.type='내과');

-- 20. Type10 - 내과를 진료하는 병원 중 달서구에 위치한 병원
(SELECT H.name FROM hospital H WHERE H.location LIKE '%달서구%')
INTERSECT
(SELECT H.name FROM hospital H, department D WHERE H.hospital_id=D.hid and D.type='내과');