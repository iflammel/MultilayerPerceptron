
public class MLP {

    double[] enters;    //входы
    double[] hidden;    //скрытые нейроны
    double outer;       //выходной нейрон
    double[][] wEH;     //вес от слоя входного к скрытому
    double[] wHO;       //вес от скрытого слоя к выходного

    double [][] patterns = {
        {0,0}, {1,0}, {0,1}, {1,1}    //массив патернов
    };

    double [] answers = {0,1,1,0};   //верные ответы

    public MLP(){
        enters = new double[patterns[0].length];
        hidden = new double[2];
        wEH = new double[enters.length][hidden.length];
        wHO = new double[hidden.length];

        InitWeigth();
    }

    public  void InitWeigth(){
        for(int i = 0; i < enters.length; i++){
            for (int j = 0; j < wEH[i].length; j++){         //инициализация весов небольшими
                wEH[i][j] = Math.random() * 0.2 + 0.1;       //случайными значениями 0<x<1
            }
        }

        for (int i = 0; i < wHO.length; i++){
            wHO[i] = Math.random() * 0.2 + 0.1;    //инициализация весов небольшими случайными значениями 0<x<1
        }
    }

    public void CountOuter(){                          //расчёт выхода
        for (int i = 0; i < hidden.length; i++){
            hidden[i] = 0;                             //обнуляем скрытый нейрон
            for (int j = 0; j < enters.length; j++){
                hidden[i] += enters[j] * wEH[j][i];    //значение входного нейрона * на вес связи
            }

            if (hidden[i] > 0.5)
                hidden[i] = 1;
            else hidden[i] = 0;
        }
        outer = 0;

        for (int i = 0; i < hidden.length; i++){
            outer += hidden[i] * wHO[i];             //вычисляем выход, скрытый нейрон * на вес
        }

        if (outer > 0.5)
            outer = 1;
        else
            outer = 0;
    }

    public  void  Study(){
        double [] err = new  double[hidden.length];        //массив ошибок
        double  gError = 0;                                //глобальная ошибка

        do {
            gError = 0;
            for (int p = 0; p < patterns.length; p++){
                for (int i = 0; i < enters.length; i++){
                    enters[i] = patterns[p][i];
                }



                CountOuter();

                double  lErr = answers[p] - outer;
                gError += Math.abs(lErr);

                for(int i = 0; i < hidden.length; i++){
                    err[i] = lErr * wHO[i];
                }


                //Тут нужно разобраться лучше
                for (int i = 0; i < enters.length; i++){
                    for (int j = 0; j < hidden.length; j++){
                        wEH[i][j] += 0.1 * err[j] * enters[i];
                    }
                }

                for (int i = 0; i < hidden.length; i++){
                    wHO[i] += 0.1 * lErr * hidden[i];
                }
            }
        }while(gError != 0);
    }

    public void Test(){
        Study();

        for (int p = 0; p < patterns.length; p++){
            for (int i = 0; i < enters.length; i++){
                enters[i] = patterns[p][i];
            }

            CountOuter();

            System.out.println(outer);
        }
    }

    public  static  void  main(String[] args){
        MLP mlp = new MLP();
        mlp.Test();
    }

}
