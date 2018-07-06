### member
> 技术栈：spring boot + spring + spring mvc + mybatis + thymeleaf

#### use redis
1. 查询product列表，使用redis sorted set结构							【ok】key/member/score(member=id,score=time)
2. 查询product categories列表，使用redis string结构，list转string		【ok】
3. 查询news列表，使用redis sorted set结构								【ok】key/member/score(member=id,score=time)
4. 查询company信息，使用redis string结构								【ok】
5. 查询webconfig信息，使用redis string结构								【ok】
6. 查询cate products列表，使用redis sorted set结构						【ok】
7. 查询product详情，使用redis string结构								【ok】
8. 查询news详情，使用redis string结构									【ok】
9. 查询column列表，使用redis string结构，list转string					【ok】
10. 查询Ads列表，使用redis string结构，list转string						【ok】
11. 查询column详情，使用redis string结构								【ok】

### TODO
1. redis尽量不设置过期时间，或者时间设置长一点，这样避免查db，尤其是sorted set类型; 需要与DB保持同步，才能避免多查DB
2. 