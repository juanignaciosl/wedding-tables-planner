# Wedding Tables Planner

I'm getting married, and arranging people at tables may get np-difficult! 

[OptaPlanner](http://www.optaplanner.org/) is an amazing piece of Open Source JBoss software that I wanted to try,
and a wedding is obviously an optimization, np-complete problem ;-) In fact this' been a straightforward application.

## What it does

You can set _knows_, _love_ and _hates_ relationships between your guests, and enter your table sizes.
Running `WeddingTablesPlanner.plan()` with a custom `DiningConfiguration` will return an optimized one, keeping loving people together,
hated people separated, and as many known people together as possible. It also minimizes the amount of tables used.  

## Try

### The hard way

Fork the project and implement your own `DiningConfigurationGenerator` with your guests and tables.

### The easy way

Grab [Wedding Tables Planner web project](https://github.com/juanignaciosl/wedding-tables-planner-web), run it and enjoy its AngularJS web interface.