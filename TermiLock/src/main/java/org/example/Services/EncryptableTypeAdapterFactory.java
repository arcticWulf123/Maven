package org.example.Services;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.*;
import org.example.Models.Encryptable;

import java.io.IOException;
import java.util.HashSet;

public class EncryptableTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        if (!Encryptable.class.isAssignableFrom(type.getRawType())) {
            return delegate;
        }

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                JsonObject obj = delegate.toJsonTree(value).getAsJsonObject();
                Encryptable enc = (Encryptable) value;

                for (String key : new HashSet<>(obj.keySet())) {
                    if (key.equals("type")) continue;
                    JsonElement el = obj.get(key);
                    if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString()) {
                        obj.addProperty(key, enc.encrypt(el.getAsString()));
                    }
                }
                Streams.write(obj, out);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonObject obj = Streams.parse(in).getAsJsonObject();

                for (String key : new HashSet<>(obj.keySet())) {
                    if (key.equals("type")) continue;
                    JsonElement el = obj.get(key);
                    if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString()) {
                        obj.addProperty(key, decrypt(el.getAsString()));
                    }
                }
                return delegate.fromJsonTree(obj);
            }

            private String decrypt(String entry) {
                char[] chars = entry.toCharArray();
                for (int i = 0; i < chars.length; i++) chars[i] -= 1;
                return String.valueOf(chars);
            }
        };
    }
}