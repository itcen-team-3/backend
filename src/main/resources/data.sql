INSERT INTO Company(company_name, registration_number,ceo_name, biz_reg_url)
VALUES('아이티센', '010-6478-3505', '안채원', 'sdf');

INSERT INTO Member(company_id, member_name, phone_number, birth_date, address, profile_image, description, role, id, password)
VALUES(1, '김관리자', '010-1111-1111', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'ADMIN', 'aaa', 'abc123'),
      (1, '이요양', '010-1234-2353', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa1', 'abc123'),
      (1, '오요양', '010-2463-4332', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa2', 'abc123'),
      (1, '안요양', '010-3474-2574', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa3', 'abc123'),
      (1, '강요양', '010-2354-3462', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa4', 'abc123'),
      (1, '유요양', '010-4653-4532', '2000-01-02', '과천시 아이티센', 'asdf', 'asdf', 'CAREGIVER', 'aa5', 'abc123');

INSERT INTO Schedule()