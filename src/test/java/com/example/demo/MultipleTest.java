package com.example.demo;

import com.example.demo.book.model.BookEntity;
import com.example.demo.book.repository.BookRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.model.TeamEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.TeamRepository;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.ProductEntity;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static com.example.demo.product.model.QProductEntity.productEntity;
import static com.example.demo.member.model.QMemberEntity.memberEntity;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserConfig.class, ProductConfig.class, MemberService.class})
public class MultipleTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @PersistenceContext(unitName = "userUnit")
    EntityManager userEntityManager;
    @PersistenceContext(unitName = "productUnit")
    EntityManager productEntityManager1;

    @Autowired
    @Qualifier("productEntityManager")
    LocalContainerEntityManagerFactoryBean productEntityManager;

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {


        User user = new User();

        user.setName("John");
        user.setEmail("john@test.com");
        user.setAge(20);
        log.info("Transcation is {}", TransactionSynchronizationManager.isActualTransactionActive());
        user = userRepository.save(user);

        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    @Ignore
    @Transactional("userTransactionManager")
    public void whenCreatingUsersWithSameEmail_thenRollback() {
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(20);
        user1 = userRepository.save(user1);
        assertNotNull(userRepository.findById(user1.getId()));
        User user2 = new User();
        user2.setName("Tom");
        user2.setEmail("john@test.com");
        user2.setAge(10);
        try {
            user2 = userRepository.save(user2);
        } catch (DataIntegrityViolationException e) {

            log.debug("error : {}", e.getMessage());
            log.info("user1 : {}", userEntityManager.contains(user1));
            log.info("user2 : {}", userEntityManager.contains(user2));
            //뭔가 user1,user2가 persistence에 없고 id가 nul이라서
//            user1.setId(1);
//            user2.setId(2);
//            Optional<User> uu = userRepository.findByEmail(user1.getEmail());
//            System.out.println(uu);
        }

        log.info("Transcation is {}", TransactionSynchronizationManager.isActualTransactionActive());

        // assertNull(userRepository.findByEmail(user2.getEmail()).orElseGet(()->null));
    }

    @Test
    @Transactional("productTransactionManager")
    public void whenCreatingProduct_thenCreated() {
        ProductEntity product = new ProductEntity();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
        product = productRepository.save(product);
        log.info("Transcation is {}", TransactionSynchronizationManager.isActualTransactionActive());
        assertNotNull(productRepository.findById(product.getId()));
    }

    @Test
    @Ignore
    @Transactional("userTransactionManager")
    public void testBookEntity() {
        log.info("testBookEntity() Transcation is {}", TransactionSynchronizationManager.isActualTransactionActive());
        BookEntity bookEntity = BookEntity.builder().name("hoho1").build();
        bookRepository.save(bookEntity);
        BookEntity ret = bookRepository.findById(bookEntity.getBookId()).orElseThrow(() -> new IllegalArgumentException("hoho"));
        assertThat(ret.getActive()).isEqualTo(true);
        ret = bookRepository.findById(10).orElseThrow(() -> new IllegalArgumentException("hoho"));
        ret.setName("hehe");
        bookRepository.save(ret);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testMemberSaveAndDelete() {
        TeamEntity team1 = new TeamEntity("team1");
        teamRepository.save(team1);

        MemberEntity member1 = new MemberEntity("회원1");
        MemberEntity member2 = new MemberEntity("회원2");

        member1.setTeamEntity(team1);
        member2.setTeamEntity(team1);
        memberRepository.save(member1);
        memberRepository.save(member2);

        teamRepository.delete(team1);

    }

    @Test
    @Transactional("userTransactionManager")
    public void testMemberSave() {
        TeamEntity team1 = new TeamEntity("team1");


        MemberEntity member1 = new MemberEntity("회원1");
        MemberEntity member2 = new MemberEntity("회원2");

        member1.setTeamEntity(team1);
        member2.setTeamEntity(team1);
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        teamRepository.save(team1);
        TeamEntity ret = teamRepository.findById(team1.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("HOHO"));
        ret.getMembers().forEach(entity -> System.out.println(entity.getName()));
        MemberEntity m1 = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("hoho"));
        MemberEntity m2 = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("hehe"));
    }

    @Test
    public void testInitial() {
        TeamEntity team1 = memberService.initial();
        team1.getMembers().forEach(entity -> assertThat(entity.getName().substring(0, 2)).isEqualTo("DS"));
        assertThat(team1.getMembers().size()).isEqualTo(4);
    }

    @Test
    @Transactional("productTransactionManager")
    public void testProduct() {
        ProductEntity p1 = ProductEntity.builder().name("p12").price(12.5).build();
        ProductEntity p2 = ProductEntity.builder().name("p22").price(13.5).build();
        ProductEntity p3 = ProductEntity.builder().name("p32").price(14.5).build();
        ProductEntity p4 = ProductEntity.builder().name("p42").price(15.5).build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);

        System.out.println(productEntityManager.getPersistenceUnitName());
        List<ProductEntity> ret = productRepository.findProductEntitiesByPrice(12.5);
        System.out.println(ret.size());

        ProductEntity p5 = ProductEntity.builder().name("DS1").price(11.5).build();
        productRepository.save(p5);
        ProductEntity expected = Optional.ofNullable(new JPAQuery<ProductEntity>(productEntityManager1)
                    .select(productEntity)
                    .from(productEntity)
                    .where(productEntity.id.eq(1))
                    .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("hooh"));
        ret = productRepository.findAll();
        System.out.println(ret.size());
        assertThat(expected.getId()).isEqualTo(1);
    }
}
