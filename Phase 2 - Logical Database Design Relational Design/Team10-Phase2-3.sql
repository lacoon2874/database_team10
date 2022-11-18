-- 1. Type1 - ���� '��'���� ȯ���� ����
SELECT * FROM patient WHERE Lname='��';

-- 2. Type1 -  ������ ������ ȯ�� ����
SELECT * FROM patient WHERE Sex='M';

-- 3. Type1 -  ������ 20���� �̻��� �Կ��� ����
SELECT * FROM ward WHERE price>=200000;

-- 4. Type2 - ġ�� ����� �ִ� ȯ�� list ��� (����+ȯ��+record = ������ ��ϵǾ��ְ� �����ϱ��� �ִ� ȯ�ڿ� ���� list ���)
SELECT distinct P.* FROM hospital H, controls C, patient P, record R WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn and P.ssn=R.patient_ssn and H.hospital_id=R.hid;

-- 5. Type2 - ���� �� �μ� �� �ǻ� ���� - ����
SELECT * FROM hospital H, department DP, doctor DT WHERE H.hospital_id=DP.hid and DP.department_code=DT.department_code AND DP.Hid=DT.Hid;

-- 6. Type3 - ������ ��ϵǾ� �ִ� ȯ�ڼ�
SELECT H.name, count(*) FROM hospital H, controls C, patient P WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn GROUP BY H.name;

-- 7. Type3 - �μ���(�μ� �̸� ����) �ǻ�� - ����
SELECT DP.type, count(*) FROM department DP, doctor DT WHERE DP.department_code=DT.department_code AND DP.Hid=DT.Hid GROUP BY DP.type;

-- 8. Type4 - �����ǻ�(id_number�� �˻�)�� ������ ���(�����б�����)�� �ǻ�� ����
SELECT * FROM doctor WHERE career=(SELECT career FROM doctor WHERE id_number='123456789');

-- 9. Type4 - �����ǻ�(id_number�� �˻�)�� ������ �������� ���� �ǻ�� ����
SELECT * FROM doctor WHERE schedule=(SELECT schedule FROM doctor WHERE id_number='123456789');

-- 10. Type5 - ���ڵ尡 ���� ȯ���� ����
SELECT * FROM patient P WHERE NOT EXISTS(SELECT 1 FROM record R WHERE P.ssn=R.patient_ssn);

-- 11. Type5 - �ܰ��� ������ �ִ� ��������
SELECT * FROM hospital H WHERE EXISTS(SELECT 1 FROM department D WHERE H.hospital_id=D.hid and D.type='�ܰ�');

-- 12. Type6 - ���� ��� �ο��� ���� �ǻ���� ����
SELECT * FROM doctor WHERE id_number IN(SELECT doctor_id_num from waiting_list where num_of_waiting<=0);

-- 13. Type7 - ���� �������� �ǻ� ������ ��� �ο��� ���� ������ ���
SELECT Fname, Lname, treatment_subject, schedule, num_of_waiting FROM
(SELECT * FROM doctor D, waiting_list W WHERE D.id_number=W.doctor_id_num)
WHERE treatment_state='������' ORDER BY num_of_waiting ASC;

-- 14. Type7 - �����ܰ����� ���ϴ� �ǻ��� �̸��� ������ ��� - (����)
SELECT * FROM
(SELECT DP.type, DT.Lname, DT.Fname, DT.schedule FROM department DP, doctor DT WHERE DP.department_code=DT.department_code and DP.Hid=DT.Hid)
WHERE type='�����ܰ�';

-- 15. Type8 - �Կ��� ������ ���� ������ ����+�Կ��� ���� ���
SELECT * FROM hospital H, ward W WHERE H.hospital_id=W.hid ORDER BY W.price ASC;

-- 16. Type8 - ��� �ο��� ���� ������� �ǻ�+��⿭ ���� ���
SELECT * FROM doctor D, waiting_list W WHERE D.id_number=W.doctor_id_num ORDER BY num_of_waiting ASC;

-- 17. Type9 - record�� ���� ������� ���, record�� ������ ������� ����
SELECT P.ssn, count(*) FROM patient P, record R WHERE P.ssn=R.patient_ssn GROUP BY P.ssn ORDER BY count(*) DESC;

-- 18. Type9 - �� ħ�� ���� ���� ������� ���
SELECT H.name, SUM(W.num_of_empty_bed) FROM hospital H, ward W WHERE H.hospital_id=W.hid GROUP BY H.name ORDER BY SUM(W.num_of_empty_bed) DESC;

-- 19. Type10 - ������ �����ϴ� ���� �� ȯ�� ���� 6�� �̻��� ���� �̸�
(SELECT H.name FROM hospital H, controls C, patient P WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn GROUP BY H.name HAVING count(*)>=6)
INTERSECT
(SELECT H.name Hname FROM hospital H, department D WHERE H.hospital_id=D.hid and D.type='����');

-- 20. Type10 - ������ �����ϴ� ���� �� �޼����� ��ġ�� ����
(SELECT H.name FROM hospital H WHERE H.location LIKE '%�޼���%')
INTERSECT
(SELECT H.name FROM hospital H, department D WHERE H.hospital_id=D.hid and D.type='����');