# BrowerInSystem

内置浏览器源码到aosp源码中，使用编写Android.bp方式进行集成

## 基于安卓13进行修改

1. 将源码对应module内的所有资源拷贝到源码中(android-13_r7/packages/apps/) 
    - Browser: ~/BrowerInSystem/Browser/Browser/src/main
    - NinjaBrowser: ~/BrowerInSystem/NinjaBrowser/app/src/main
2. 将系统编译文件中增加对应内置app
    - device中增加对应配置： 
        - `~/android-13_r7/device/generic/common/gsi_product.mk:22:    Browser \`
        - `~/android-13_r7/device/generic/common/gsi_product.mk:23:    NinjaBrowser \`
    - build中增加对应配置： 
        - `~/android-13_r7/build/make/target/product/handheld_product.mk:25:    Browser \` 
        - `~/android-13_r7/build/make/target/product/handheld_product.mk:26:    NinjaBrowser \` 
