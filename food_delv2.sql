-- @C:\Users\Saathvik\Desktop\food_delv2.sql;
drop table address;
drop table upi_details;
drop table card_details;
drop table order_list;
drop table orders;
drop table coupon;
drop table delivery_partner;
drop table dishes;
drop table restaurant;
drop table customer;
drop table location;


create table customer(
    cust_id varchar(30) constraint cust_id_pk primary key,
    password varchar(30) constraint cust_pw_len check (LENGTH(password) >= 8) constraint cust_pw_nn not null,
    cust_name varchar(20) constraint cust_name_nn not null,
    phone_no number(10) constraint cust_ph_len check (LENGTH(TO_CHAR(phone_no))=10) constraint cust_ph_nn not null
);

create table location(
    pincode number(6) constraint loc_pin_len check (LENGTH(TO_CHAR(pincode))=6) constraint loc_pin_pk primary key,
    area varchar(30),
    city varchar(30)
);

create table address(
    cust_id varchar(30),
    street_address varchar(100),
    pincode number(6) constraint addr_pin_fk references location(pincode),
    constraint addr_comp_key primary key (cust_id, street_address, pincode),
    constraint addr_cid_fk foreign key (cust_id) references customer(cust_id) on delete cascade
);

create table restaurant(
    rest_id char(4) constraint rest_id_ck check (rest_id like 'R%') constraint rest_id_pk primary key,
    rest_name varchar(30) constraint rest_name_nn not null,
    phone number(10) constraint rest_ph_len check (LENGTH(TO_CHAR(phone))=10) constraint rest_ph_nn not null,
    street_address varchar(100) constraint rest_addr_nn not null,
    pincode number(6) constraint rest_pin_fk references location(pincode),
    cuisine varchar(20),
    open_time timestamp,
    close_time timestamp,
    rating number(1) constraint rest_rate_ck check(rating between 1 and 5)
);

create table dishes(
    dish_id char(4) constraint dish_id_ck check (dish_id like 'D%') constraint dish_id_pk primary key,
    rest_id char(4) constraint dish_rid_fk references restaurant(rest_id),
    dish_name varchar(30) constraint dish_name_nn not null,
    dish_type varchar(20),
    unit_price number(6,2) constraint dish_price_nn not null
);

create table delivery_partner(
    delv_partner_id char(4) constraint delv_id_ck check (delv_partner_id like 'P%') constraint delv_id_pk primary key,
    delivery_partner_name varchar(30) constraint delv_name_nn not null,
    phone number(10) constraint delv_ph_len check (LENGTH(TO_CHAR(phone))=10) constraint delv_ph_nn not null,
    license_no char(15) constraint delv_licen_nn not null,
    rating number(1) constraint delv_rate_ck check(rating between 1 and 5)
);

create table coupon(
    coupon_id char(4) constraint coup_id_ck check (coupon_id like 'I%') constraint coup_id_pk primary key,
    rest_id char(4) constraint coup_rid_fk references restaurant(rest_id),
    coupon_name varchar(30),
    discount number(2),
    valid_upto date
);

create table orders(
    order_no char(4) constraint ord_id_ck check (order_no like 'O%') constraint order_no_pk primary key,
    cust_id varchar(30) constraint ord_cust_fk references customer(cust_id),
    rest_id char(4) constraint ord_rest_fk references restaurant(rest_id),
    delv_partner_id char(4) constraint ord_did_fk references delivery_partner(delv_partner_id),
    order_time timestamp,
    delv_time timestamp,
    bill_amt number(7,2),
    coupon_id char(4) constraint ord_coupid_fk references coupon(coupon_id)
);

create table order_list(
    order_no char(4) constraint ol_ordno_fk references orders(order_no),
    dish_id char(4) constraint ol_did_fk references dishes(dish_id),
    qty number(3),
    constraint ol_comp_key primary key (order_no, dish_id)
);

create table card_details(
    card_no number(9) constraint card_no_pk primary key,
    cust_id varchar(30) constraint card_cid_fk references customer(cust_id),
    exp_date date constraint card_exp_nn not null,
    name_on_card varchar(30) constraint card_name_nn not null
);

create table upi_details(
    upi_id varchar(30) constraint upi_id_pk primary key,
    cust_id varchar(30) constraint upi_cid_fk references customer(cust_id)
);

--TRIGGER
SELECT * FROM upi_details;

CREATE OR REPLACE TRIGGER update_bill_amt
AFTER INSERT ON order_list
FOR EACH ROW
DECLARE
    v_unit_price number(7,2);
BEGIN
    SELECT unit_price INTO v_unit_price
    FROM dishes
    WHERE dish_id = :new.dish_id;

    UPDATE orders
    SET bill_amt = bill_amt + (:new.qty * v_unit_price)
    WHERE order_no = :new.order_no;
END;
/

-- Insert data into customer table
INSERT INTO customer VALUES ('manju123', 'qwerty123', 'Manjummel', 9902142378);
INSERT INTO customer VALUES ('kishendas', 'kishu@1992', 'Kishen Das', 9650617203);
INSERT INTO customer VALUES ('shreedevil', 'srisri@@@', 'Shreedevi', 9728345612);
INSERT INTO customer VALUES ('7wenty', 'shetha999', 'Shwetha Suresh', 9839417203);
INSERT INTO customer VALUES ('saaathvick', 'goat1000','Saathvik Balaji', 8928374718);
INSERT INTO customer VALUES ('moorthi', 'moormarket', 'Moorthi Pranav', 7820617563);
INSERT INTO customer VALUES ('sammy', 'sam@88877', 'Sam Yukesh', 7862457293);
INSERT INTO customer VALUES ('sayaa', 'sayamaya', 'Saya Sorna', 7891237638);

select * from customer;

-- Insert data into location table
INSERT INTO location VALUES (600083, 'Ashoknagar', 'Chennai');
INSERT INTO location VALUES (600090, 'Besantnagar', 'Chennai');
INSERT INTO location VALUES (600005, 'Chepauk', 'Chennai');
INSERT INTO location VALUES (600008, 'Egmore', 'Chennai');
INSERT INTO location VALUES (600020, 'Adyar', 'Chennai');
INSERT INTO location VALUES (600010, 'Kilpauk', 'Chennai');
INSERT INTO location VALUES (600107, 'Koyambedu', 'Chennai');
INSERT INTO location VALUES (600034, 'Nungambakkam', 'Chennai');
INSERT INTO location VALUES (600015, 'Saidapet', 'Chennai');
INSERT INTO location VALUES (600113, 'Tidel Park', 'Chennai');
INSERT INTO location VALUES (600042, 'Velacheri', 'Chennai');
INSERT INTO location VALUES (600041, 'Thiruvanmiyur', 'Chennai');


select * from location;

-- Insert data into address table
INSERT INTO address VALUES ('manju123', '123 Main St', 600083);
INSERT INTO address VALUES ('manju123', 'FB801 Grand Apt', 600041);
INSERT INTO address VALUES ('kishendas', '456 Elm St', 600090);
INSERT INTO address VALUES ('shreedevil', '789 Pine St', 600005);
INSERT INTO address VALUES ('shreedevil', '41B Beam Apt', 600113);
INSERT INTO address VALUES ('7wenty', '101 Maple Ave', 600008);
INSERT INTO address VALUES ('7wenty', '21 3rd Main Block', 600005);
INSERT INTO address VALUES ('7wenty', 'A306 Manas', 600020);
INSERT INTO address VALUES ('saaathvick', '202 Oak Dr', 600020);
INSERT INTO address VALUES ('saaathvick', '#21 North Madras Road', 600015);
INSERT INTO address VALUES ('saaathvick', '8th floor, Branchwood', 600083);
INSERT INTO address VALUES ('moorthi', '303 Birch Rd', 600010);
INSERT INTO address VALUES ('sammy', '404 Cedar Ln', 600107);
INSERT INTO address VALUES ('sayaa', '505 Walnut St', 600034);

select * from address;

-- Insert data into restaurant table
INSERT INTO restaurant VALUES ('R001', 'Spice Hub', 8123772628, '789 Food St', 600090, 'Indian', TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('22:00:00', 'HH24:MI:SS'), 5);
INSERT INTO restaurant VALUES ('R002', 'Mc Donalds', 9876543210, 'Golf Course Road', 600008, 'Fast Food', TO_TIMESTAMP('09:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('23:00:00', 'HH24:MI:SS'), 2);
INSERT INTO restaurant VALUES ('R003', 'Pasta Palace', 8765432109, '123 Pasta St', 600083, 'Italian', TO_TIMESTAMP('10:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('22:00:00', 'HH24:MI:SS'), 4);
INSERT INTO restaurant VALUES ('R004', 'Sushi World', 7654321098, '456 Sushi Ave', 600005, 'Japanese', TO_TIMESTAMP('11:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('23:00:00', 'HH24:MI:SS'), 5);
INSERT INTO restaurant VALUES ('R005', 'Burger King', 6543210987, '789 Burger Blvd', 600010, 'Fast Food', TO_TIMESTAMP('09:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('22:00:00', 'HH24:MI:SS'), 3);
INSERT INTO restaurant VALUES ('R006', 'Veggie Delight', 5432109876, '101 Veggie St', 600034, 'Vegetarian', TO_TIMESTAMP('08:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('22:00:00', 'HH24:MI:SS'), 4);
INSERT INTO restaurant VALUES ('R007', 'Pizza Place', 4321098765, '202 Pizza Rd', 600107, 'Italian', TO_TIMESTAMP('10:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('23:00:00', 'HH24:MI:SS'), 5);
INSERT INTO restaurant VALUES ('R008', 'Taco Town', 3210987654, '303 Taco Ln', 600020, 'Mexican', TO_TIMESTAMP('11:00:00', 'HH24:MI:SS'), TO_TIMESTAMP('22:00:00', 'HH24:MI:SS'), 4);

select * from restaurant;

-- Insert data into dishes table
INSERT INTO dishes VALUES ('D001', 'R001', 'Paneer Tikka', 'Veg', 200.00);
INSERT INTO dishes VALUES ('D002', 'R001', 'Butter Naan', 'Veg', 50.00);
INSERT INTO dishes VALUES ('D003', 'R001', 'Chicken Biryani', 'Non-Veg', 250.00);
INSERT INTO dishes VALUES ('D004', 'R001', 'Dal Makhani', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D005', 'R001', 'Mango Lassi', 'Veg', 80.00);

INSERT INTO dishes VALUES ('D006', 'R002', 'Chicken Maharaja Mac', 'Non-Veg', 350.00);
INSERT INTO dishes VALUES ('D007', 'R002', 'Paneer Maharaja Mac', 'Veg', 300.00);
INSERT INTO dishes VALUES ('D008', 'R002', 'French Fries', 'Veg', 100.00);
INSERT INTO dishes VALUES ('D009', 'R002', 'McFlurry', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D010', 'R002', 'Coke', 'Veg', 50.00);

INSERT INTO dishes VALUES ('D011', 'R003', 'Spaghetti Bolognese', 'Non-Veg', 250.00);
INSERT INTO dishes VALUES ('D012', 'R003', 'Margherita Pizza', 'Veg', 220.00);
INSERT INTO dishes VALUES ('D013', 'R003', 'Garlic Bread', 'Veg', 100.00);
INSERT INTO dishes VALUES ('D014', 'R003', 'Minestrone Soup', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D015', 'R003', 'Tiramisu', 'Veg', 180.00);

INSERT INTO dishes VALUES ('D016', 'R004', 'Sushi Platter', 'Non-Veg', 400.00);
INSERT INTO dishes VALUES ('D017', 'R004', 'Vegetarian Sushi', 'Veg', 350.00);
INSERT INTO dishes VALUES ('D018', 'R004', 'Tempura', 'Non-Veg', 250.00);
INSERT INTO dishes VALUES ('D019', 'R004', 'Miso Soup', 'Veg', 100.00);
INSERT INTO dishes VALUES ('D020', 'R004', 'Green Tea Ice Cream', 'Veg', 120.00);

INSERT INTO dishes VALUES ('D021', 'R005', 'Whopper', 'Non-Veg', 300.00);
INSERT INTO dishes VALUES ('D022', 'R005', 'Veggie Burger', 'Veg', 250.00);
INSERT INTO dishes VALUES ('D023', 'R005', 'Onion Rings', 'Veg', 100.00);
INSERT INTO dishes VALUES ('D024', 'R005', 'Chocolate Shake', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D025', 'R005', 'Caesar Salad', 'Veg', 180.00);

INSERT INTO dishes VALUES ('D026', 'R006', 'Grilled Veggie Sandwich', 'Veg', 180.00);
INSERT INTO dishes VALUES ('D027', 'R006', 'Falafel Wrap', 'Veg', 200.00);
INSERT INTO dishes VALUES ('D028', 'R006', 'Hummus and Pita', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D029', 'R006', 'Greek Salad', 'Veg', 180.00);
INSERT INTO dishes VALUES ('D030', 'R006', 'Baklava', 'Veg', 200.00);

INSERT INTO dishes VALUES ('D031', 'R007', 'Pepperoni Pizza', 'Non-Veg', 280.00);
INSERT INTO dishes VALUES ('D032', 'R007', 'Four Cheese Pizza', 'Veg', 260.00);
INSERT INTO dishes VALUES ('D033', 'R007', 'Garlic Knots', 'Veg', 120.00);
INSERT INTO dishes VALUES ('D034', 'R007', 'Caesar Salad', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D035', 'R007', 'Chocolate Brownie', 'Veg', 180.00);

INSERT INTO dishes VALUES ('D036', 'R008', 'Beef Tacos', 'Non-Veg', 220.00);
INSERT INTO dishes VALUES ('D037', 'R008', 'Veggie Tacos', 'Veg', 200.00);
INSERT INTO dishes VALUES ('D038', 'R008', 'Nachos', 'Veg', 180.00);
INSERT INTO dishes VALUES ('D039', 'R008', 'Churros', 'Veg', 150.00);
INSERT INTO dishes VALUES ('D040', 'R008', 'Guacamole', 'Veg', 120.00);


select * from dishes;

-- Insert data into delivery_partner table
INSERT INTO delivery_partner VALUES ('P001', 'Vidya', 8939275342, 'DL3784927583721', 2);
INSERT INTO delivery_partner VALUES ('P002', 'Gopal', 9003777055, 'DL8294017463526', 2);
INSERT INTO delivery_partner VALUES ('P003', 'Suresh', 9887766554, 'DL7283649502847', 3);
INSERT INTO delivery_partner VALUES ('P004', 'Lakshmi', 9823456789, 'DL2345678901234', 4);
INSERT INTO delivery_partner VALUES ('P005', 'Rahul', 9876543210, 'DL1234567890123', 5);
INSERT INTO delivery_partner VALUES ('P006', 'Arjun', 9998887776, 'DL9988776655443', 3);
INSERT INTO delivery_partner VALUES ('P007', 'Rohit', 9955664433, 'DL4455667788990', 4);
INSERT INTO delivery_partner VALUES ('P008', 'Meera', 9876567890, 'DL5566778899001', 5);
INSERT INTO delivery_partner VALUES ('P009', 'Divya', 9878787878, 'DL3344556677889', 4);
INSERT INTO delivery_partner VALUES ('P010', 'Amit', 9988776655, 'DL2233445566778', 3);

select * from delivery_partner;

-- Insert data into coupon table
INSERT INTO coupon VALUES ('I001', 'R001', 'SPICE10', 10, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I002', 'R002', 'MCD15', 15, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I003', 'R003', 'ITALY20', 20, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I004', 'R004', 'SUSHI25', 25, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I005', 'R005', 'BURGER10', 10, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I006', 'R006', 'HEALTH15', 15, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I007', 'R007', 'PIZZA20', 20, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I008', 'R008', 'TACO25', 25, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I009', 'R001', 'INDIAN10', 10, TO_DATE('2024-12-31', 'YYYY-MM-DD'));
INSERT INTO coupon VALUES ('I010', 'R002', 'BBQ15', 15, TO_DATE('2024-12-31', 'YYYY-MM-DD'));


select * from coupon;

-- Insert data into orders table
INSERT INTO orders VALUES ('O001', 'manju123', 'R007', 'P003', TO_TIMESTAMP('2024-06-01 12:30:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I001');
INSERT INTO orders VALUES ('O002', 'kishendas', 'R004', 'P001', TO_TIMESTAMP('2024-06-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-01 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I002');
INSERT INTO orders VALUES ('O003', 'shreedevil', 'R002', 'P003', TO_TIMESTAMP('2024-06-02 12:45:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-02 13:15:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I003');
INSERT INTO orders VALUES ('O004', '7wenty', 'R008', 'P008', TO_TIMESTAMP('2024-06-02 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-02 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I004');
INSERT INTO orders VALUES ('O005', 'saaathvick', 'R004', 'P005', TO_TIMESTAMP('2024-06-03 11:30:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I005');
INSERT INTO orders VALUES ('O006', 'moorthi', 'R003', 'P006', TO_TIMESTAMP('2024-06-03 14:15:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-03 14:45:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I006');
INSERT INTO orders VALUES ('O007', 'moorthi', 'R007', 'P009', TO_TIMESTAMP('2024-06-04 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-04 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I007');
INSERT INTO orders VALUES ('O008', 'sayaa', 'R008', 'P007', TO_TIMESTAMP('2024-06-04 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-04 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I008');
INSERT INTO orders VALUES ('O009', '7wenty', 'R003', 'P008', TO_TIMESTAMP('2024-06-05 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-05 12:30:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I009');
INSERT INTO orders VALUES ('O010', 'kishendas', 'R001', 'P010', TO_TIMESTAMP('2024-06-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-06-05 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 0, 'I010');

select * from orders;

-- Insert data into order_list table
INSERT INTO order_list VALUES ('O001', 'D025', 2);
INSERT INTO order_list VALUES ('O002', 'D018', 1);
INSERT INTO order_list VALUES ('O002', 'D013', 2);
INSERT INTO order_list VALUES ('O003', 'D002', 3);
INSERT INTO order_list VALUES ('O003', 'D027', 1);
INSERT INTO order_list VALUES ('O003', 'D040', 2);
INSERT INTO order_list VALUES ('O004', 'D035', 1);
INSERT INTO order_list VALUES ('O005', 'D010', 3);
INSERT INTO order_list VALUES ('O006', 'D016', 2);
INSERT INTO order_list VALUES ('O006', 'D022', 1);
INSERT INTO order_list VALUES ('O007', 'D037', 2);
INSERT INTO order_list VALUES ('O008', 'D031', 1);
INSERT INTO order_list VALUES ('O009', 'D029', 3);
INSERT INTO order_list VALUES ('O010', 'D023', 2);

select * from order_list;

-- Insert data into card_details table
INSERT INTO card_details VALUES (736473847, 'manju123', TO_DATE('2025-12-31', 'YYYY-MM-DD'), 'Manju Nath');
INSERT INTO card_details VALUES (143526738, 'kishendas', TO_DATE('2026-12-31', 'YYYY-MM-DD'), 'Kishen Das');
INSERT INTO card_details VALUES (902357182, 'kishendas', TO_DATE('2025-11-19', 'YYYY-MM-DD'), 'Kishen Das');
INSERT INTO card_details VALUES (325678912, 'shreedevil', TO_DATE('2026-09-15', 'YYYY-MM-DD'), 'Shreedevi');
INSERT INTO card_details VALUES (678912345, '7wenty', TO_DATE('2027-03-22', 'YYYY-MM-DD'), 'Shwetha Suresh');
INSERT INTO card_details VALUES (890123456, 'saaathvick', TO_DATE('2025-10-10', 'YYYY-MM-DD'), 'Saathvik Balaji');
INSERT INTO card_details VALUES (901234567, 'moorthi', TO_DATE('2026-07-05', 'YYYY-MM-DD'), 'Moorthi Pranav');
INSERT INTO card_details VALUES (123456789, 'sammy', TO_DATE('2025-08-18', 'YYYY-MM-DD'), 'Sam Yukesh');
INSERT INTO card_details VALUES (234567890, 'sammy', TO_DATE('2025-09-30', 'YYYY-MM-DD'), 'Saya Sorna');

select * from card_details;

-- Insert data into upi_details table
INSERT INTO upi_details VALUES ('manjunath@okhdfc', 'manju123');
INSERT INTO upi_details VALUES ('kishendas@oksbi', 'kishendas');
INSERT INTO upi_details VALUES ('shreedevil@okicici', 'shreedevil');
INSERT INTO upi_details VALUES ('shwethasuresh@okaxis', '7wenty');
INSERT INTO upi_details VALUES ('saathvik@okhdfc', 'saaathvick');
INSERT INTO upi_details VALUES ('moorthipranav@okicici', 'moorthi');
INSERT INTO upi_details VALUES ('samyukesh@okaxis', 'sammy');
INSERT INTO upi_details VALUES ('sayasorna@okhdfc', 'sayaa');

commit;