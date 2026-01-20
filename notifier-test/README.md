## 🧪 Tests

```bash
cd notifier-test
mvn test
```

- name: Upload bundle to Sonatype Central
  run: |
  curl -u "$CENTRAL_USERNAME:$CENTRAL_PASSWORD" \
  -X POST \
  -F "bundle=@target/central-publishing/central-bundle.zip" \
  https://central.sonatype.com/api/v1/publisher/upload
  env:
  CENTRAL_USERNAME: ${{ secrets.CENTRAL_USERNAME }}
  CENTRAL_PASSWORD: ${{ secrets.CENTRAL_PASSWORD }}