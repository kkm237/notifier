package io.github.kkm237.notifier.core.model;


import io.github.kkm237.notifier.core.exceptions.NotifierException;
import io.github.kkm237.notifier.core.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * mainly for email
 */
public final class AttachmentPayload {

    private final String filename;
    private final String contentType;
    private final byte[] content;
    private final File file;
    private final InputStream inputStream;
    private final String description;


    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public File getFile() {
        return file;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CourierAttachment{" +
                "filename='" + filename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content=" + Arrays.toString(content) +
                ", file=" + file +
                ", inputStream=" + inputStream +
                ", description='" + description + '\'' +
                '}';
    }

    private AttachmentPayload(Builder builder) {
        this.filename = builder.filename;
        this.contentType = builder.contentType;
        this.description = builder.description;
        this.content = builder.content;
        this.file = builder.file;
        this.inputStream = builder.inputStream;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String filename;
        private String contentType;
        private String description = "N/A";
        private byte[] content;
        private File file;
        private InputStream inputStream;


        private Builder() {}

        public Builder filename(String filename){
            if (StringUtils.isNullOrEmpty(filename)) throw new NotifierException("Filename cannot be null or empty");
            this.filename = filename;
            return this;
        }
        public Builder contentType(String contentType){
            if (StringUtils.isNullOrEmpty(contentType)) throw new NotifierException("Content type cannot be null or empty");
            this.contentType = contentType;
            return this;
        }

        public Builder content(byte[] content){
            if (content == null) throw new NotifierException("Content cannot be null");
            this.content = content;
            return this;
        }

        public Builder description(String description){
            if (StringUtils.isNullOrEmpty(description)) throw new NotifierException("Description cannot be null or empty");
            this.description = description;
            return this;
        }

        public Builder file(File file){
            if (file == null) throw new NotifierException("File cannot be null");
            this.file = file;
            return this;
        }

        public Builder inputStream(InputStream inputStream){
            if (inputStream == null) throw new NotifierException("InputStream cannot be null");
            this.inputStream = inputStream;
            return this;
        }

        public AttachmentPayload build() {

            if (filename.isEmpty()) {
                throw new NotifierException("filename is required");
            }

            if (contentType.isEmpty()) {
                throw new NotifierException("contentType is required");
            }

            if (description.isEmpty()) {
                throw new NotifierException("description is required");
            }

            if (content != null && file != null && inputStream != null) {
                throw new NotifierException("You must not specified multiple datasource");
            }

            if (content == null && file == null && inputStream == null) {
                throw new NotifierException("You have not specified datasource");
            }

            if (content != null && file != null) {
                throw new NotifierException("You must not specified multiple datasource");
            }

            if (content != null && inputStream != null) {
                throw new NotifierException("You must not specified multiple datasource");
            }

            if (content == null && file != null && inputStream != null) {
                throw new NotifierException("You must not specified multiple datasource");
            }

            return new AttachmentPayload(this);
        }
    }
}