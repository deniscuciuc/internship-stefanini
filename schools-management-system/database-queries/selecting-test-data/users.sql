-- Find users by course name and role
select distinct users.id, users.email, users.is_active, users.profile_id, users.role
from users
         join users_courses uc on users.id = uc.user_id
         join courses c on uc.course_id = c.id
where users.role = 'STUDENT'
  and c.course_name like '%Engl%'
limit 0, 3;