package com.example.demo;

import com.example.demo.ChildParent.CategoryEntity;
import com.example.demo.ChildParent.ChildParentEntity;
import com.example.demo.model.FieldExam;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChildParentTest {
    @Test
    public void childparentTest() {
        List<ChildParentEntity> list = new ArrayList<>();
        list.add(ChildParentEntity.builder().
                name("G").guid("c8e").parent(null).build());
        list.add(ChildParentEntity.builder().
                name("Z").guid("b11").parent("c8e").build());
        list.add(ChildParentEntity.builder().
                name("A").guid("a11").parent("c8e").build());
        list.add(ChildParentEntity.builder().
                name("C").guid("c11").parent("c8e").build());
        list.add(ChildParentEntity.builder().
                name("F").guid("d2c").parent("b11").build());
        list.add(ChildParentEntity.builder().
                name("L").guid("a24").parent(null).build());
        list.add(ChildParentEntity.builder().
                name("K").guid("cd3").parent("a24").build());
        list.add(ChildParentEntity.builder().
                name("O").guid("abc").parent("cd3").build());
        List<ChildParentEntity> roots = new ArrayList<>();
        Map<String, ChildParentEntity> all = new HashMap<>();


        Collections.sort(list, Comparator.comparing(ChildParentEntity::getGuid));

        System.out.println(list);

        list.stream().forEach(entity -> all.put(entity.getGuid(), entity));
        for (String guid : all.keySet()) {
            ChildParentEntity entity = all.get(guid);
            if (Objects.isNull(entity.getParent())) {
                roots.add(entity);
            } else if (all.containsKey(entity.getParent())) {
                ChildParentEntity parent = all.get(entity.getParent());
                parent.addEntity(entity);
            } else {
                // parent가 없는 경우이니 이는 IllegalArgumentException
                throw new IllegalArgumentException("no valid parent vendorCategoryID");
            }
        }
        System.out.println(all);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fieldExamNotContinuousTest() {
        FieldExam f = new FieldExam();
        f.setField(Arrays.asList(1, 2, null, 4, 5, 6, 7, 8, null, 10, 11, 12));
        f.getList().stream().forEach(System.out::println);
    }

    @Test
    public void fieldExamContinuousTest() {
        FieldExam f = new FieldExam();
        f.setField(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        f.getList().stream().forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        CategoryEntity c1 = CategoryEntity.builder()
                .id(1)
                .name("name1")
                .build();
        c1.addChildren(CategoryEntity.builder()
                .id(11)
                .name("name11")
                .build());
        c1.addChildren(CategoryEntity.builder()
                .id(12)
                .name("name12")
                .build());
        CategoryEntity c2 = CategoryEntity.builder()
                .id(2)
                .name("name2")
                .build();
        c2.addChildren(CategoryEntity.builder()
                .id(21)
                .name("name21")
                .build());
        c2.addChildren(CategoryEntity.builder()
                .id(22)
                .name("name21")
                .build());
        CategoryEntity c221 = CategoryEntity.builder()
                .id(221)
                .name("name22")
                .build();
        c2.getChildren().get(1).addChildren(c221);
        CategoryEntity c222 = CategoryEntity.builder()
                .id(222)
                .name("name22")
                .build();
        c2.getChildren().get(1).addChildren(c222);
        c221.addChildren(
                CategoryEntity.builder()
                        .id(2211)
                        .name("name2211")
                        .build()
        );
        c221.addChildren(
                CategoryEntity.builder()
                        .id(2212)
                        .name("name2212")
                        .build()
        );
        c222.addChildren(
                CategoryEntity.builder()
                        .id(2221)
                        .name("name2221")
                        .build()
        );
        CategoryEntity c2222 = CategoryEntity.builder()
                .id(2222)
                .name("name2222")
                .build();
        c222.addChildren(c2222);
        c2222.addChildren(
                CategoryEntity.builder()
                        .id(22221)
                        .name("name2222Final1")
                        .build()
        );
        c2222.addChildren(
                CategoryEntity.builder()
                        .id(22222)
                        .name("name2222Final2")
                        .build()
        );
        c2222.addChildren(
                CategoryEntity.builder()
                        .id(22223)
                        .name("name2222Final3")
                        .build()
        );
        c2222.addChildren(
                CategoryEntity.builder()
                        .id(22224)
                        .name("name2222Final4")
                        .build()
        );

        // 현재에 아래 children을 모두 flatMap한 것을 추가한다!
        List<CategoryEntity> ret = flatMapAll(Arrays.asList(c1, c2));
        ret.addAll(Arrays.asList(c1, c2));
        ret.add(CategoryEntity.builder()
                .id(1)
                .name("name1")
                .build());
        Comparator<CategoryEntity> comparator = Comparator.comparing(CategoryEntity::getId).reversed().thenComparing(CategoryEntity::getName);
        ret = ret.stream().sorted(comparator).collect(Collectors.toList());
        Map<Integer, Map<String, List<CategoryEntity>>> map = ret.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getId, Collectors.groupingBy(CategoryEntity::getName)));
        System.out.println(map);
    }

    private List<CategoryEntity> flatMapAll(List<CategoryEntity> here) {
        List<CategoryEntity> tmp = here.stream()
                .map(entity -> entity.getChildren())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (tmp.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        tmp.addAll(flatMapAll(tmp));
        return tmp;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMap() {
        List<Integer> list = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());
        list.add(null);
        Optional.ofNullable(list.get(10)).orElseThrow(() -> new IllegalArgumentException());
    }

    @Test
    public void testOptional() {
        List<Integer> list = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());
        list.add(null);
        System.out.println(Optional.ofNullable(list.get(10)).orElseGet(() -> null));
    }

    @Test
    public void testOptionalMap() {
        CategoryEntity c1 = CategoryEntity.builder()
                .id(1)
                .name("name1")
                .build();
        c1.addChildren(CategoryEntity.builder()
                .id(11)
                .name("name11")
                .build());
        c1.addChildren(CategoryEntity.builder()
                .id(12)
                .name("name12")
                .build());
        System.out.println(Optional.ofNullable(c1).map(entity -> {
            System.out.println("first depth");
            return entity.getChildren().get(0);
        })
                .map(entity -> {
                    System.out.println("second depth");
                    return entity.getChildren().size() > 0 ? entity.getChildren().get(0) : null;
                })
                .map(entity -> {
                    System.out.println("third depth");
                    return entity;
                })
                .orElseGet(() -> null));
    }
}
