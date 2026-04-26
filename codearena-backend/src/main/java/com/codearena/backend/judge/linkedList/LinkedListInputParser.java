package com.codearena.backend.judge.linkedList;


public class LinkedListInputParser {

    public static ListNode parse(String input) {

        input = input.trim();

        if (input.equals("[]")) return null;

        // remove [ and ]
        input = input.substring(1, input.length() - 1);

        String[] parts = input.split(",");

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        for (String part : parts) {
            int value = Integer.parseInt(part.trim());
            current.next = new ListNode(value);
            current = current.next;
        }

        return dummy.next;
    }
}