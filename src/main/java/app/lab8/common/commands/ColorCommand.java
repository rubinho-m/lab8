package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;

public class ColorCommand extends CommandTemplate implements CommandWithResponse {

    private String output;

    private int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    @Override
    public void execute(String user) throws Exception {
        String color = getDbHandler().getColor(getArg());
        if (color == null) {
            int randomInt = rnd(0, 16777215);
            System.out.println(randomInt);
            String tmpColor = Integer.toHexString(randomInt);
            String randomColor = "";
            int zero = 6 - tmpColor.length();
            for (int i = 0; i < zero; i++) {
                randomColor += "0";

            }
            randomColor += tmpColor;
            getDbHandler().setColor(getArg(), randomColor);
            output = randomColor;
        } else {
            output = color;
        }
    }

    @Override
    public Response getCommandResponse() {
        System.out.println(output);
        return new Response(output);
    }
}
