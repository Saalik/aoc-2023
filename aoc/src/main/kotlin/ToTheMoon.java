public class ToTheMoon {
    public static void main(String[] args) {
        final var resFinal = 9;
        int[] prices = new int[] {1, 3, 2, 8, 4, 10};
        boolean hold = false;
        final var FRAIS = 2;
        int res = 0;
        var currValue = 0;

        for (int i = 0; i < prices.length; i++) {
            if (hold) {
                // On regarde si la vente est profitable
                if ( prices[i] > currValue+FRAIS ){
                    res += prices[i];
                    hold = false;
                }

            } else {
                // On achète
                currValue = prices[i];
                res-= FRAIS;
                hold = true;
            }

        }


        System.out.println("My profits are "+res);

        assert(res == resFinal);
    }

}
