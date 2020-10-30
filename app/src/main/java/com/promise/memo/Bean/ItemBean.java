package com.promise.memo.Bean;

import java.util.List;

public class ItemBean {
    String desc;
    String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    List<Person> personList;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public static class Person{
        String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
