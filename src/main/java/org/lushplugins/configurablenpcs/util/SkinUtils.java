package org.lushplugins.configurablenpcs.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mineskin.JsoupRequestHandler;
import org.mineskin.MineSkinClient;
import org.mineskin.data.Skin;
import org.mineskin.request.GenerateRequest;
import org.mineskin.response.GenerateResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class SkinUtils {
    private static final MineSkinClient MINESKIN_CLIENT = MineSkinClient.builder()
        .requestHandler(JsoupRequestHandler::new)
        .userAgent("ConfigurableNPCs")
        .build();

    public static CompletableFuture<Skin> generateSkin(String base64) {
        String url = getRawUrlFromBase64(base64);

        try {
            GenerateRequest.url(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            return MINESKIN_CLIENT.generate().submitAndWait(GenerateRequest.url(url)).thenApply(GenerateResponse::getSkin);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL getUrlFromBase64(String base64) {
        try {
            String rawUrl = getRawUrlFromBase64(base64);
            if (rawUrl == null) {
                return null;
            }

            return URI.create(rawUrl).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(base64 + " does not appear to be a valid texture.", e);
        }
    }

    public static String getRawUrlFromBase64(String base64) {
        String dataRaw = new String(Base64.getDecoder().decode(base64)).toLowerCase();
        JsonObject data = JsonParser.parseString(dataRaw).getAsJsonObject();

        String rawUrl;
        try {
            rawUrl = data.get("textures").getAsJsonObject().get("skin").getAsJsonObject().get("url").getAsString();
        } catch (NullPointerException e) {
            throw new RuntimeException(base64 + " does not appear to be a valid texture.", e);
        }

        return rawUrl;
    }
}