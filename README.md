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

## 基于安卓10进行修改

> Browser貌似集成不了，有bug，需要调整

1. 将源码对应module内的所有资源拷贝到源码中(android_10.0.0_r41/packages/apps/) 
    - NinjaBrowser: ~/BrowerInSystem/NinjaBrowser_android10/app/src/main
2. 将系统编译文件中增加对应内置app
    - 修改方案在对应结果的目录中增加配置即可
        - 文件: `build/make/target/product/handheld_product.mk`
            - 增加 `NinjaBrowser \`
        - 文件: `build/make/target/product/mainline_arm64.mk`
            -  增加`system/app/NinjaBrowser/NinjaBrowser.apk \`
3. 此时编译还需要将**Android.bp**中的`package`和`license`部分去除，貌似安卓10不支持
4. **NinjaBrowser**项目的`AndroidManifest.xml`中存储部分兼容需要调整,去除`preserveLegacyExternalStorage`和`requestLegacyExternalStorage`
```
android:preserveLegacyExternalStorage="true"
android:requestLegacyExternalStorage="true"
```
