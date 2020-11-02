public class Main {
    public static void main(String[] args) {
        goldenRatioMethod();
        System.out.println("############################");
        tangentMethod();
    }

    //Метод золого сечения
    public static void goldenRatioMethod(){
        int count = 0;
        double xMin;
        double eps = 10e-10;
        Function func = new Function(3, 5, eps, true);

        while(true){
            count++;
            if(func.ifMin()) {
                xMin = func.findApMin();
                break;
            }

            if(func.f(func.x) > func.f(func.y)){
                func.a = func.x;
                func.x = func.y;
                func.y = func.b + func.a - func.x;
            }
            else{
                func.b = func.y;
                func.y = func.x;
                func.x = func.a + func.b - func.y;
            }
        }

        System.out.println("Итераций: " + count);
        System.out.println("xMin: " + xMin);
        System.out.println("f(xMin): " + func.f(xMin));
    }

    //Метод Касательных
    public static void tangentMethod(){
        int count = 0;
        double xMin = 0;
        double eps = 10e-10;
        Function func = new Function(3, 5, eps);
        boolean search = true; //Переменная, показывающая, нужен ли поиск или нет (на случай, если функция монотонно убывает/возрастает)

        //Проверка на монотонность
        if(func.check()){
            search = false;
            count++;
            System.out.println(count + ": a = " + func.a + ",  b = " + func.b + ";");
            xMin = func.f(func.a) < func.f(func.b) ? func.a : func.b;
        }

        while(search) {
            count++;
            func.x = func.findX();
            System.out.println(count + ": a = " + func.a + ",  b = " + func.b + ";");

            if (func.difF(func.x) < -eps) {
                func.a = func.x;
            } else if (func.difF(func.x) > eps) {
                func.b = func.x;
            } else {
                xMin = func.x;
                break;
            }
        }
        System.out.println("\nВсего итераций: " + count);
        System.out.println("xMin: " + xMin);
        System.out.println("f(xMin): " + func.f(xMin));
    }
}

class Function{
    //(x-4)^2 + ln(x); [3,5] э x
    public double a;
    public double b;
    public double x;
    public double y;
    double eps;

    public Function(double a, double b, double eps){
        this.a = a;
        this.b = b;
        this.eps = eps;
        //golden();
    }

    public Function(double a, double b, double eps, boolean ifGolden){
        this.a = a;
        this.b = b;
        this.eps = eps;
        if(ifGolden) {
            golden();
        }
    }

    //Золотое сечение
    public double findApMin(){
        return (a+b)/2;
    }
    public void golden(){
        double delta = ((b-a)*(3 - Math.sqrt(5)))/2;
        this.x = a + delta;
        this.y = b - delta;
    }
    public boolean ifMin(){
        return((this.b - this.a) < 2 * eps);
    }

    //Метод касательных
    public double difF(double arg){return (2*(arg - 4) + 1/arg);} //возвращает значение производной в точке arg
    public double findX() {return (f(b)-f(a)+difF(a)*a - difF(b)*b)/(difF(a)-difF(b));} //находит икс на пересечении касательных
    public boolean check(){return this.difF(a) > eps || difF(b) < -eps;} //Проверка на монотонность

    //Общее
    public double f(double arg){ return ( (arg- 4) * (arg-4) + Math.log(arg) );}
}
