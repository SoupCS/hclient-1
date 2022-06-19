package neko.itskekoffcode.clientapi.utils;

import neko.itskekoffcode.clientapi.utils.RandomUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NickNameHelper {
    private final List<String> nicks = new CopyOnWriteArrayList();
    private int number = 0;
    public String nickname;
    public void loadNicknames() {
        for (int i = 0; i < 150000; ++i) {
            nickname = RandomUtils.randomStringMinecraft();
            nicks.add(nickname);
        }
    }

    public String nextNick() {
        ++this.number;
        if (this.number >= this.nicks.size()) {
            this.number = 0;
        }

        return this.nicks.get(this.number);
    }
}
