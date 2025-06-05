INSERT INTO Company(company_name, registration_number,ceo_name, biz_reg_url)
VALUES('아이티센', '010-6478-3505', '안채원', 'sdf');

INSERT INTO Member(company_id, member_name, phone_number, birth_date, address, profile_image_url, description, role, certificate_number, career, login_id, login_pw)
VALUES(1, '김관리자', '010-1111-1111', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'ADMIN', 'aaa', 1,'abc123', '234'),
      (1, '이요양', '010-1234-2353', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa1', 2,'abc123','234'),
      (1, '오요양', '010-2463-4332', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa2', 3,'abc123','234'),
      (1, '안요양', '010-3474-2574', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa3', 1,'abc123','234'),
      (1, '강요양', '010-2354-3462', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa4', 2,'abc123','234'),
      (1, '유요양', '010-4653-4532', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa5', 2,'abc123','234');

INSERT INTO Member(company_id, member_name, phone_number, birth_date, address, profile_image_url, description, role, certificate_number, career,login_id, login_pw)
VALUES(1, '김환자', '010-1111-1111', '2000-01-02', '부산 수영구', 'asdf', 'asdf', 'PATIENT', '2234',2,'aaa', 'abc123'),
      (1, '이환자', '010-1111-1111', '2000-01-02', '대구 수성구', 'asdf', 'asdf', 'PATIENT', '2234',2,'aaa', 'abc123'),
      (1, '안환자', '010-1111-1111', '2000-01-02', '서울 강서구', 'asdf', 'asdf', 'PATIENT', '2234',2,'aaa', 'abc123'),
      (1, '오환자', '010-1111-1111', '2000-01-02', '제주도 서귀포시', 'asdf', 'asdf', 'PATIENT', '2234',2,'aaa', 'abc123');


INSERT INTO Attendance_Log(member_id, check_in, check_out, check_in_status, check_out_status)
VALUES (2, '2025-06-05T14:30:00','2025-06-05T20:30:00','LATE','ON_TIME');

INSERT INTO Attendance_Log(member_id, check_in, check_out, check_in_status, check_out_status)
VALUES (2, '2025-06-10T14:30:00','2025-06-10T14:30:00','ON_TIME', 'EARLY_LEAVE'),
       (2, '2025-06-12T14:30:00','2025-06-12T14:30:00','ON_TIME', 'ON_TIME');

INSERT INTO Attendance_Log(member_id, check_in, check_out, check_in_status, check_out_status)
    VALUES
(2, '2025-06-10T18:30:00','2025-06-10T20:30:00','LATE', 'ON_TIME');