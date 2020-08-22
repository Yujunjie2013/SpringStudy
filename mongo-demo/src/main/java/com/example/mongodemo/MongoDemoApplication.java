package com.example.mongodemo;

import com.example.mongodemo.model.Coffee;
import com.example.mongodemo.repository.CoffeeRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableMongoRepositories
public class MongoDemoApplication implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CoffeeRepository coffeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }

    //演示使用mongoTemplate
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Coffee espresso = Coffee.builder()
//                .name("espresso")
//                .price(new BigDecimal(100))
//                .createTime(new Date())
//                .updateTime(new Date()).build();
//        Coffee coffee = mongoTemplate.save(espresso);
//        log.info("Coffee save {}", coffee);
//
//        List<Coffee> coffees = mongoTemplate.find(Query.query(Criteria.where("name").is("espresso")), Coffee.class);
//
//        log.info("Find {} Coffee", coffees.size());
//
//        Thread.sleep(1000);//为了看更新时间
//
//        //更新价格
//        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("espresso")),
//                new Update().set("price", new BigDecimal(600)).currentDate("updateTime"), Coffee.class);
//        log.info("Update Result: {}", updateResult);
//        Coffee updateOne = mongoTemplate.findById(coffee.getId(), Coffee.class);
//        log.info("Find Update Result: {}", updateOne);
//
//        mongoTemplate.remove(updateOne);
//    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //演示使用Repository
        Coffee espresso = Coffee.builder().name("espresso").price(new BigDecimal(12)).createTime(new Date()).updateTime(new Date()).build();
        Coffee latte = Coffee.builder().name("latte").price(new BigDecimal(22)).createTime(new Date()).updateTime(new Date()).build();


        coffeeRepository.insert(Arrays.asList(espresso, latte));

        coffeeRepository.findAll(Sort.by("name"))
                .forEach(c -> {
                    log.info("Saved Coffee {}", c);
                });

        Thread.sleep(1000);

        latte.setPrice(new BigDecimal(222));
        latte.setUpdateTime(new Date());

        coffeeRepository.save(latte);

        coffeeRepository.findByName("latte")
                .forEach(c -> {
                    log.info("findByName Coffee {}", c);
                });

        coffeeRepository.deleteAll();
    }
}
