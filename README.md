# NO LONGER MAINTAINED / MAINTAINER WANTED

It was great while it lasted, folks, but I no longer use this software and
don't have the time to maintain and support it. For the most part, it still
works fine and many people still use it on a regular basis.

If you are interested in taking over the maintenance and support of this project, please get in touch with me.

# CropPlanning

**Crop Planning Software** for small farmers and serious gardeners.
Plan for and manage the myriad crops, varieties and plantings required to keep a modern, intensive market garden producing throughout the growing season. This is a cross-platform (Windows, Mac, Unix, etc) desktop application that allows small farmers and gardeners to: Create, duplicate and delete crops and plantings; inherit data from other plantings and crops; key in a few data and let the program calculate the rest for you; sort and filter your plantings. These are all possible and new features are being added all the time. The software has been released as **open source, free software** and therefore is and will always be available **free of charge**. Those who [**donate**](Donate), however, will be thanked profusely.

### Latest Version
Version: **0.7.0** <br>
Released: **Feb 8, 2016** <br>
[Release Notes](https://github.com/claytonrcarter/cropplanning/wiki/ReleaseNotes070) <br>
[Installation Instructions](https://github.com/claytonrcarter/cropplanning/wiki/SetupAndInstallation) <br>

[Download for Windows](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0-Win.zip)  <br>
[Download for Mac](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0-Mac.zip)
(for MacOS 10.8+: [read this](https://github.com/claytonrcarter/cropplanning/wiki/MacGatekeeper))  <br>
[Download for Linux](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0.zip)

## Start Here

* Download the software. [Windows](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0-Win.zip) [Mac](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0-Mac.zip) [Linux and everyone else](https://github.com/claytonrcarter/cropplanning/releases/download/v0.7.0/CropPlanning-0.7.0.zip)
* See the program in action, including weekly planting lists and screencasts (New!).
* [Learn how to use the program](https://github.com/claytonrcarter/cropplanning/wiki/ExamplesAndScreenShots), including it's advanced features.
* Contact us at [cropplanning@gmail.com](mailto:cropplanning@gmail.com) with questions, concerns, suggestions, and such.
* Help us! Right now, the best way to help us is to try out the software and [tell us](mailto:cropplanning@gmail.com) what you think about it.

## Features

* familiar, spreadsheet-like interface
* easily create and print weekly field planting and GH seeding lists
* sort and filter your crop plans so you only see what you want to see
* powerful database makes it easier to work with your plans and keep data linked together
* easily keep track of whether something has been planted, transplanted and harvested
* easily keep track of the actual and planned dates upon which something is planted, transplanted and harvested
* data can be "linked" from crops ==> varieties ==> individual plantings
* enter certain values and have others calculated automatically; for example:
 * enter a number of beds or row-feet to plant and the program can calculate how many transplants you'll need
 * enter a desired yield quantity and the program can tell you how many row-feet or beds you should plant
 * enter your desired date of harvest and the program can estimate when to plant
* import & export data
* bed and row lengths can be set for every field or even for every bed
* Missed a planting? Don't delete it, just ignore it (ie, "skip" it) to get it out of the way.
* Plantings can be explicitly marked as direct seeded or transplanted and can have different planting data accordingly.
* Help you estimate how much seed you'll need to execute your plan
* Support for US and metric measurements
* charts and stats that show:
 * beds in use in each field by week
 * trays in the greenhouse by week
 * how many trays needed (tallied up by size) for the season
 * how many beds have "requirements" (ie, black plastic, reemay, etc) for the season

### Support Provided By

MOFGA, Fedco Seeds, Northeast SARE

### Free Software

This project is released as free, open source software, which means that the computer source code that makes it work is and will always be available for free. In addition, this project is built upon and couldn't exist without these other free, open source projects:

| HSQLDB | Persist | Glazed Lists | iText | Twinkle | GitX |
| ------ | ------- | ------------ | ----- | ------- | ---- |
| DBMS (Database) | ORM (Super easy interface to database) | Super easy and fast list sorting, filtering and more! | PDF creation | Sparkle-style updates for Java! | Great git app for Mac |

### Important Notice: Beta Software

This software is currently under development and is changing rapidly. Please DO download it, try it out and let us know what you think; but please keep in mind that it's not finished. Is is great? Does it suck? Does something about it really annoy you or can you simply not farm without it? Please let us know!

## Developing and Contributing Code

To download the code yourself simply

    git clone https://github.com/claytonrcarter/cropplanning.git

or of course fork it into your own github account.

You will need:

*  Netbeans and Java 1.5
*  A bundle of libraries
    * [Download libraries](http://sourceforge.net/projects/cropplanning/files/Developer%20Files/devel-libs.zip/download)

If you unzip the library bundle in the parent folder of the cloned `cropplanning` repo, NetBeans should find all of the libraries automatically.
