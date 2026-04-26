package com.codearena.backend.judge.array;

import java.util.Map;

public class BestTimeToBuyAndSellStockSolution implements ArraySolution {

    @Override
    public Object execute(Map<String, Object> inputMap) {

        int[] prices =
                ArrayInputParser.getIntArray(inputMap, "prices");

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }

        return maxProfit;
    }

    @Override
    public boolean matches(Object actualOutput, String expectedOutputJson) {

        int expected =
                ArrayOutputParser.asInt(
                        ArrayOutputParser.parseRaw(expectedOutputJson)
                );

        return ((Integer) actualOutput) == expected;
    }

    @Override
    public String inputKey() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inputKey'");
    }
}
