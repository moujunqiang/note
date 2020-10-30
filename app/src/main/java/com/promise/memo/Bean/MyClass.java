package com.promise.memo.Bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public static void main(String[] args) {
        List<ItemBean> itemBeans =new ArrayList<>();
        ItemBean itemBean = new ItemBean();
        itemBean.setDesc("《玩具总动员》是皮克斯的动画系列电影，截止2019年共制作了四部，由华特·迪士尼影片公司和皮克斯动画工作室合作推出。讲述了主角两个玩具牛仔警长胡迪和太空骑警巴斯光年的故事。");
        itemBean.setName("玩具总动员");
        itemBean.setImage("http://img5.imgtn.bdimg.com/it/u=3235558524,4212892161&fm=26&gp=0.jpg");
        List<ItemBean.Person> personList =new ArrayList<>();
        personList.add(new ItemBean.Person("胡迪"));
        personList.add(new ItemBean.Person("巴斯光年"));
        personList.add(new ItemBean.Person("土豆头"));
        personList.add(new ItemBean.Person("暴暴龙"));
        personList.add(new ItemBean.Person("草莓熊"));
        personList.add(new ItemBean.Person("安迪"));
        itemBean.setPersonList(personList);
        itemBeans.add(itemBean);
        ItemBean itemBean1 = new ItemBean();
        itemBean1.setDesc("在另外的世界里住着那样一群怪物，他们长相滑稽，却必须装作狰狞。因为，在夜深的时候，他们要悄悄的出现在孩子的衣橱里，吓唬刚刚甜美睡去的宝宝。一切，只因为他们是怪物电力公司的员工，而孩子的尖叫，则是怪物王国发电的全部能量。怪物们害怕孩子，认为孩子们的一切都会对怪兽造成伤害，却又必须让孩子尖叫。在怪物的国度里，他们都是为全民造福的英雄");
        itemBean1.setName("怪兽电力公司");
        itemBean1.setImage("http://timg02.bdimg.com/timg?wapbaike&quality=60&size=b1250_800&sec=1349839550&di=70eea1aa92dfb961c3dcce769b624c81&src=http://imgsrc.baidu.com/baike/pic/item/08f790529822720efc8b909e73cb0a46f21fab09.jpg");
        List<ItemBean.Person> personList1 =new ArrayList<>();
        personList1.add(new ItemBean.Person("麦克华夫斯基"));
        personList1.add(new ItemBean.Person("苏利文"));
        itemBean1.setPersonList(personList1);
        itemBeans.add(itemBean1);
        ItemBean itemBean2 = new ItemBean();
        itemBean2.setDesc("鲍勃是一个超人特工，他惩恶扬善，深受街坊邻里的爱戴。“不可思议”先生就是他的光荣外号。他和另一个超人特工“弹力女超人”相爱，两人结婚后过上平静的生活");
        itemBean2.setName("超人总动员");
        itemBean2.setImage("http://up.enterdesk.com/edpic/fd/08/73/fd087324e526ab2df4e0019cf23bd595.jpg");
        List<ItemBean.Person> personList2 =new ArrayList<>();
        personList2.add(new ItemBean.Person("弹力女超人"));
        personList2.add(new ItemBean.Person("超能先生"));
        personList2.add(new ItemBean.Person("酷冰侠"));
        personList2.add(new ItemBean.Person("超劲先生"));

        itemBean1.setPersonList(personList2);
        itemBeans.add(itemBean2);
        ItemBean itemBean3 = new ItemBean();
        itemBean3.setImage("http://img5.imgtn.bdimg.com/it/u=3425127230,3923879089&fm=26&gp=0.jpg");
        itemBean3.setDesc("故事讲述了地球上的清扫型机器人瓦力偶遇并爱上了机器人伊娃后，追随她进入太空历险的一系列故事。影片的全球票房累计超过5.3亿美元，曾获得第81届奥斯卡最佳动画长片奖");
        itemBean3.setName("机器人总动员");
        List<ItemBean.Person> personList3 =new ArrayList<>();
        personList3.add(new ItemBean.Person("伊娃"));
        personList3.add(new ItemBean.Person("瓦力"));

        itemBean1.setPersonList(personList3);
        itemBeans.add(itemBean3);
        ItemBean itemBean4= new ItemBean();
        itemBean4.setDesc("一个鞋匠家庭出身的12岁墨西哥小男孩米格，自幼有一个音乐梦，但音乐却是被家庭所禁止的，他们认为自己被音乐诅咒了。在米格秘密追寻音乐梦时，因为触碰了一把吉他而踏上了亡灵土地。每年的亡灵节日，逝去的家人都会返回人间与亲人团聚，但从来还没有人去到过亡灵的世界。米格被多彩绚丽的亡灵世界所震撼，而更令他的惊喜的是，他重逢了失去的太爷爷和祖辈们，一家人要想办法将米格重新送回人间");
        itemBean4.setName("寻梦环游记");
        itemBean4.setImage("http://5b0988e595225.cdn.sohucs.com/images/20171220/3b75384a98cc49258e6fc6b4eb9d7dbe.jpeg");
        List<ItemBean.Person> personList4 =new ArrayList<>();
        personList4.add(new ItemBean.Person("米格"));
        personList4.add(new ItemBean.Person("可可"));
        personList4.add(new ItemBean.Person("埃克托"));
        itemBean1.setPersonList(personList4);
        itemBeans.add(itemBean4);
        Gson gson =new Gson();
        System.out.println(gson.toJson(itemBeans));
        System.out.println(new Gson().fromJson("[{\"desc\":\"《玩具总动员》是皮克斯的动画系列电影，截止2019年共制作了四部，由华特·迪士尼影片公司和皮克斯动画工作室合作推出。讲述了主角两个玩具牛仔警长胡迪和太空骑警巴斯光年的故事。\",\"personList\":[{\"name\":\"胡迪\"},{\"name\":\"巴斯光年\"},{\"name\":\"土豆头\"},{\"name\":\"暴暴龙\"},{\"name\":\"草莓熊\"},{\"name\":\"安迪\"}],\"name\":\"玩具总动员\"},{\"desc\":\"在另外的世界里住着那样一群怪物，他们长相滑稽，却必须装作狰狞。因为，在夜深的时候，他们要悄悄的出现在孩子的衣橱里，吓唬刚刚甜美睡去的宝宝。一切，只因为他们是怪物电力公司的员工，而孩子的尖叫，则是怪物王国发电的全部能量。怪物们害怕孩子，认为孩子们的一切都会对怪兽造成伤害，却又必须让孩子尖叫。在怪物的国度里，他们都是为全民造福的英雄\",\"personList\":[{\"name\":\"米格\"},{\"name\":\"可可\"},{\"name\":\"埃克托\"}],\"name\":\"怪兽电力公司\"},{\"desc\":\"鲍勃是一个超人特工，他惩恶扬善，深受街坊邻里的爱戴。“不可思议”先生就是他的光荣外号。他和另一个超人特工“弹力女超人”相爱，两人结婚后过上平静的生活\",\"name\":\"超人总动员\"},{\"desc\":\"故事讲述了地球上的清扫型机器人瓦力偶遇并爱上了机器人伊娃后，追随她进入太空历险的一系列故事。影片的全球票房累计超过5.3亿美元，曾获得第81届奥斯卡最佳动画长片奖\",\"name\":\"机器人总动员\"},{\"desc\":\"一个鞋匠家庭出身的12岁墨西哥小男孩米格，自幼有一个音乐梦，但音乐却是被家庭所禁止的，他们认为自己被音乐诅咒了。在米格秘密追寻音乐梦时，因为触碰了一把吉他而踏上了亡灵土地。每年的亡灵节日，逝去的家人都会返回人间与亲人团聚，但从来还没有人去到过亡灵的世界。米格被多彩绚丽的亡灵世界所震撼，而更令他的惊喜的是，他重逢了失去的太爷爷和祖辈们，一家人要想办法将米格重新送回人间\",\"name\":\"寻梦环游记\"}]\n", new TypeToken<List<ItemBean>>(){}.getType()).toString());
    }

}
