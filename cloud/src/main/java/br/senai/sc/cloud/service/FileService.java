/**
 * Serviço responsável por gerenciar arquivos no sistema.
 *
 * @author Kaue Correa
 */
package br.senai.sc.cloud.service;

import br.senai.sc.cloud.model.File;
import br.senai.sc.cloud.model.Task;
import br.senai.sc.cloud.repository.FileRepository;
import br.senai.sc.cloud.repository.TaskRepository;
import br.senai.sc.cloud.service.interfaces.FileServiceInt;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements FileServiceInt {

    @NonNull
    private FileRepository fileRepository;

    @Value("${aws.access.key}")
    private String awsKey;

    @Value("${aws.access.secrety.key}")
    private String awsSecretyKey;

    @Value("${aws.bucket.name}")
    private String awsBucketName;


    public Boolean adicionarFile(Long id, MultipartFile file) {

        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKey, awsSecretyKey);

            AmazonS3 client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1).build();

            boolean bucketExist = client.doesBucketExistV2(awsBucketName);
            if (!bucketExist) {
                return false;
            }

            Task tarefa = new Task();
            tarefa.setId(id);

            File fileSec = new File();
            fileSec.setTask(tarefa);
            fileSec.setDate(new Date());
            String uuid = UUID.randomUUID().toString();
            fileSec.setReference(uuid);
            fileRepository.save(fileSec);

            /**
             * Cria metadados do arquivo
             */
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            client.putObject(awsBucketName, uuid, file.getInputStream(), objectMetadata);

            return true;

        } catch (AmazonS3Exception e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String buscarFilePorID(Long id) {

        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File não encontrado"));

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKey, awsSecretyKey);

        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1).build();

        /**
         * Requisição para gerar uma URL.
         */
        GeneratePresignedUrlRequest presigned =
                new GeneratePresignedUrlRequest(awsBucketName, file.getReference());
        presigned.setExpiration(new Date(System.currentTimeMillis() + 600000));

        String url = client.generatePresignedUrl(presigned).toString();

        return url;
    }
}