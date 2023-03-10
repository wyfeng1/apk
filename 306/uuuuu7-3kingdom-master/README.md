# 帝王三国单机版

#### 介绍

<img src="https://images.gitee.com/uploads/images/2021/0510/210732_79abbf6f_2202850.png" width="200px" />
支持二維碼催更 :joy: 



### 单机版帝王三国大纲

###### 开发原则
    1)设计分辨率 1160 * 640 
    2)命名规范：
            文件名：下划线
            函数名、类名：小写开头 驼峰
            结构体定义：大写开头 驼峰
    3)开发引擎：cocos creator 2.4.0
    4)编写语言：Typescript
    5)动画dragonBones：帧数 12帧 （肉眼的极限分辨帧数是动画片12帧,电影24帧）
###### 一、城市页面
    1)基本信息页（均能看见）
        名字、规模、繁荣度、城市天赋、所属国家、城主、 
        驻防数量、
        按钮：攻占按钮、城池管理按钮

    2)城池管理面板（只有自己是城主才有按钮）
        A) 详情
            士兵: 伤兵，预备兵，闲兵 
            城防: 100000/1000000
            资源: 铜币，粮食    
            税收: 1000铜币/月   1000粮食/月
        B) 建筑
            a) 大厅（不可拆 可升级） 增加税收
            b) 酒馆 ======> 招募面板
            c) 教场 ======> 闲兵，预备兵，伤兵
            d) 市场 ======> 转换粮食、铜币
            e) 监狱 ======> 俘虏
            f) 步兵 弓兵 机械 骑兵（可以拆）===> 训练相关兵种
            g) 民居 增加每回合预备兵数量 
            h) 农场 可以收取粮食
        C) 英雄 
            状态 驻防、休息


###### 二、将领
    1) 属性页 
    2) 装备页 更换装备弹板 
    3) 配兵页 
    4) 编队页面
    5）武将的生成 
        a）超流     主属性 110-120    副属性100-110  成长90-95     突围95-99  
        b）一流     主属性 100-110    副属性90-100   成长85-89     突围90-95    
        c）二流     主属性 95-105     副属性80-90    成长80-85     突围85-90    
        d）三流     主属性 90-100     副属性80-90    成长75-80     突围80-85  
        e）优秀     主属性 85-95      副属性70-80    成长70-75     突围75-80       
        f）良好     主属性 80-90      副属性70-80    成长60-70     突围70-75        
        g）普通     主属性 40-79      副属性40-70    成长40-60     突围60-70      
    6）武将爆点公式
        范围(闭区间)：  
        talent [40 ~ 99]
        level  [1) ~ 99]    
        成长参数1: 1.6、2.0、2.2、2.6 
        成长参数2: 4、6、9、13
        f1)= (talent * 成长参数1)+ 成长参数2)) * (level - 1) / 98;
    7）武将经验公式
        15 * (等级 - 1) * (等级 - 1) * 等级 * 等级 / 4
    
###### 三、大地图
    1）每月结算一次
    2）普通速度 一天为实际时间的60s  快速为30s  慢速为120s 
    3）每10秒 遍历一次电脑 进攻的条件 （可以携带的兵满了，进攻方将领数量大于防守方数量）
    4）侦查数据

###### 四、国家
    1) 基本信息:  英雄、城池、 钱、粮
    2) 科技: []

###### 五、战斗
    1) 战斗类型: 遭遇战，攻城战，普通的副本战斗
    2) 副本: 
        a) 黄巾: 1-10级黄巾、渠帅、主帅
        b) 矿: 材料、金矿
    3) 涉及将领数据、世界时间、科技等级、国家数据
    4) 将领战斗中消耗 体力、兵力，
                增加 经验、

###### 六、副本 
    1）夺城 
    2）挑战神仙

###### 七、结算部分
    1）显示的信息
        城池数，武将数，
    2）招募（ 如果在城市中 ）
    3）探索（ 如果在城市中，可以消耗体力 ）
    4）扣除工资（ 武将，士兵 ） 
    5）扣除医药费（ 伤兵 ）

###### 八、储物箱界面 
    1)基本物品的面板（宝物、装备） ✅
        宝物：使用按钮 丢弃按钮
        装备：强化按钮 丢弃按钮

    2)强化装备的弹板 ✅
        装备强化概率：1 -（rate / 50）
        材料消耗数量：1 2 3 5 7 9 

###### 九、设置
    1)音乐 
    2)难度
    3)游戏速度 （ 慢 快 正常 ）

###### 十、通用颜色
    tab   亮 #FFC700
          暗 #896500
    title #55402d
    text  重 #774700
    text  浅 #9a7c4a

###### 鸣谢 
    帝三吧友：
        李嘉图铭路提供了详细的公式
        江南总都督探讨了很多想法
        一路以来支持我的朋友们

###### 现阶段剩余任务
    1 月结算 
    2 战斗结算
    3 装备 
    4 大地图 
    5 攻城战斗
    6 编队
    7 城市建筑

###### 设计思路与源码分享 
    1）大地图的设计思路 
    2）战斗
        a)问题需要解决的问题 
            1 攻击根据兵种优先 
            2 攻击伤害有兵种克制







