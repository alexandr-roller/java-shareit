insert into bookings(start_date, end_date, item_id, booker_id, status)
values
-- past
(DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', -9, CURRENT_TIMESTAMP), 1, 3, 'APPROVED'),
--current, approved
(DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', 1, CURRENT_TIMESTAMP), 1, 3, 'APPROVED'),
--future, rejected
(DATEADD('DAY', 2, CURRENT_TIMESTAMP), DATEADD('DAY', 3, CURRENT_TIMESTAMP), 1, 3, 'REJECTED'),
--future, waiting
(DATEADD('DAY', 3, CURRENT_TIMESTAMP), DATEADD('DAY', 4, CURRENT_TIMESTAMP), 1, 3, 'WAITING');
