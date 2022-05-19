INSERT INTO tag (name)
VALUES ('tagName1');

INSERT INTO tag (name)
VALUES ('tagName3');

INSERT INTO tag (name)
VALUES ('tagName5');

INSERT INTO tag (name)
VALUES ('tagName4');

INSERT INTO tag (name)
VALUES ('tagName7');

INSERT INTO tag (name)
VALUES ('tagName6');



INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate1', 'description1', 10.1, 1, '2022-05-10T20:54:58.369Z', '2022-05-10T20:54:58.369Z');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate3', 'description3', 30.3, 3, '2022-05-10T20:58:38.323Z', '2022-05-10T20:54:58.369Z');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate2', 'description2', 20.2, 2, '2022-05-08T20:54:58.369Z', '2022-05-10T20:54:58.369Z');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate4', 'description4', 26.2, 4, '2022-05-10T20:44:58.369Z', '2022-05-10T20:54:58.369Z');




INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (3, 2);
