with c_user as (select movie_id,rating from user_movie
				where user_id='660'),
     ranks as (select um.user_id,x.movie_id,x.rating*um.rating as rank
     		   from c_user x, user_movie um where um.movie_id in
     		   (select movie_id from c_user)),
     bonds as (select user_id, sum(rank) as bond from ranks
     		   group by user_id),
     r_pool as (select movie_id, rating*bond as rating007
     			from user_movie natural join bonds
     			where movie_id not in
     			(select movie_id from c_user))
          
          select movie_id,sum(rating007) as final_rank
          from r_pool group by movie_id
          order by final_rank desc;