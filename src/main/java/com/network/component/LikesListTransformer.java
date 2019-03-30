package com.network.component;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class LikesListTransformer {

    public String serialize(List<Integer> likes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream outputStream = new ObjectOutputStream(baos)) {
            outputStream.writeObject(likes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public List<Integer> deserialize(String str) {
        if(str != null) {
            byte[] data = Base64.getDecoder().decode(str);
            try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                return (ArrayList<Integer>) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
