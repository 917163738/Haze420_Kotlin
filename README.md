# ☝ Campuswire mobile

> The RN app for Campuswire

[![Build Status](https://travis-ci.com/CampusTech/mobile.svg?token=vzC3S48TGP2sPzWsR1qe&branch=dev)](https://travis-ci.com/CampusTech/mobile)
![Platform - Android and iOS](https://img.shields.io/badge/platform-Android%20%7C%20iOS-yellow.svg)


__Clone repo__

`git clone git@github.com:CampusTech/mobile.git`

__Install RN dependencies__

https://facebook.github.io/react-native/docs/getting-started.html#installing-dependencies

__Setup Xcode__

https://facebook.github.io/react-native/docs/getting-started.html#xcode

__Install nvm__

https://github.com/creationix/nvm#install-script

__Install app depdendencies__

`npm install -g yarn`

`yarn`

__Update node_modules/react-native/react.gradle

- add follwing script to react.gradle file after end of `doFirst`

               
                doLast {
                def moveFunc = { resSuffix ->
                    File originalDir = file("${resourcesDir}/drawable-${resSuffix}");
                    if (originalDir.exists()) {
                        File destDir = file("$buildDir/../src/main/res/drawable-${resSuffix}");
                        ant.move(file: originalDir, tofile: destDir);
                    }
                }
                moveFunc.curry("ldpi").call()
                moveFunc.curry("mdpi").call()
                moveFunc.curry("hdpi").call()
                moveFunc.curry("xhdpi").call()
                moveFunc.curry("xxhdpi").call()
                moveFunc.curry("xxxhdpi").call()
            }
         

__Build app__

`react-native run-ios`
`react-native run-android`

PS: run `react-native run-ios --simulator="iPhone Xs"` if you want to run app on particular version of simulator

__See React Native's 'Getting Started' for additional info__

https://facebook.github.io/react-native/docs/getting-started.html

