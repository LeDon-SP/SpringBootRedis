package com.le.controller;

import com.le.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class StudentController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void set(@RequestBody Student student){
        redisTemplate.opsForValue().set("student",student);
    }

    @GetMapping("/get/{key}")
    public Student get(@PathVariable("key") String key){
        return (Student) redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key){
        redisTemplate.delete(key);
        return !redisTemplate.hasKey(key);
    }

    @GetMapping("/string")
    public String stringTest(){
        redisTemplate.opsForValue().set("str","hello world");
        String str = (String) redisTemplate.opsForValue().get("str");
        return str;
    }

    @GetMapping("/list")
    public List<String> listTest(){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush("student","张三");
        listOperations.rightPush("student","李四");
        listOperations.rightPush("student","王五");
        List<String> list = listOperations.range("student",0,-1);
        return list;
    }

    @GetMapping("/set")
    public Set<String> setTest(){
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add("student1","张三","王五","李四","张三","王五");
        setOperations.add("student1","赵六");
        Set<String> set = setOperations.members("student1");
        return set;
    }

    @GetMapping("/zset")
    public Set<String> zsetTest(){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset","张三",1);
        zSetOperations.add("zset","王五",3);
        zSetOperations.add("zset","李四",2);
        Set<String> set = zSetOperations.range("zset",0,-1);
        return set;
    }

    @GetMapping("/hash")
    public String hashTest(){
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("people","name","张三");
        String str = hashOperations.get("people", "name");
        return str;
    }
}
