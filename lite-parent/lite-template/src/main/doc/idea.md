
2013-11-30  itwarcraft@gmail.com 一个想法
制作1个引擎，直接采用java代码来写，提供一个类或接口，采用注解的方式，每个页面对应一个类，同时，为了提高复用程序，
把页面分解成一个一个的小的控件，通过组合的方式添加到一起，比如可以集成table等
可以想办法，在生产环境，生成对应的模板文件，从而提高效果，而在开发过程中，可以直接调用类，每个类都可以设定成单例，并且是final类型的
类似与：
@Page("index")
class page.....


@ViewComponent(...)


通过不同的组装，生成不同的界面
