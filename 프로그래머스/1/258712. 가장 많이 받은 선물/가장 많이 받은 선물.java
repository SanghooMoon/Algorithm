import java.util.*;

class Data {
    private final Map<String, Integer> giveGifts = new HashMap<>(); // 주고받은선물
    private int giftCount = 0; // 선물지수

    // 기초 데이터 세팅
    public Data(String[] friends) {
        for (String friend : friends) {
            giveGifts.put(friend, 0);
        }
    }

    // 내가 준 선물 저장
    public void setGiveGifts(String targetName) {
        giveGifts.put(targetName, giveGifts.getOrDefault(targetName, 0) + 1);
        giftCount++;
    }

    // 내가 받은 선물 저장
    public void setReceiveGifts(String targetName) {
        giveGifts.put(targetName, giveGifts.getOrDefault(targetName, 0) - 1);
        giftCount--;
    }

    // 내가 더 줘서 몇 개 받는지 계산
    public int calcResult(String[] friends) {
        int result = 0;
        for (String friend : friends) {
            Integer count = giveGifts.get(friend);
            if (count > 0) {
                result++;
            }
        }
        return result;
    }

    // 나랑 선물을 주고받지 않은 사람
    public List<String> listNoGifts(String[] friends) {
        List<String> list = new ArrayList<>();
        for (String friend : friends) {
            Integer count = giveGifts.get(friend);
            if (count == 0) {
                list.add(friend);
            }
        }
        return list;
    }

    public int getGiftCount() {
        return giftCount;
    }
}
class Solution {
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;
        HashMap<String, Data> dataMap = new HashMap<>();

        // 초기 데이터 세팅
        for (String friend : friends) {
            dataMap.put(friend, new Data(friends));
        }
        // 선물 주고 받기
        for (String gift : gifts) {
            String[] giftInfo = gift.split(" ");
            String from = giftInfo[0];
            String to = giftInfo[1];

            dataMap.get(from).setGiveGifts(to);
            dataMap.get(to).setReceiveGifts(from);
        }
        // 최종 계산
        for (String friend : friends) {
            Data data = dataMap.get(friend);
            int getCount = data.calcResult(friends); // 내가 더 많이줘서 받아야할 갯수

            // 나랑 선물을 주고받지 않은 사람 중 선물지수 높은 수
            List<String> list = data.listNoGifts(friends);
            for (String noFriend : list) {
                if (dataMap.get(noFriend).getGiftCount() < data.getGiftCount()) {
                    getCount++;
                }
            }
            answer = Math.max(answer, getCount);
        }
        return answer;
    }
}