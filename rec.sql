with c_user as (select music_id,rating from user_music
				where user_id='1'),
     ranks as (select um.user_id,x.song_id,x.rating*um.rating as rank
     		   from c_user x, user_music um where um.song_id in
     		   (select song_id from c_user)),
     bonds as (select user_id, sum(rank) as bond from ranks
     		   group by user_id),
     r_pool as (select song_id, rating*bond as rating007
     			from user_music natural join bonds
     			where song_id not in
     			(select song_id from c_user))
     select song_id, sum(rating007) as final_rank
     from r_pool
     group by song_id
     order by final_rank;