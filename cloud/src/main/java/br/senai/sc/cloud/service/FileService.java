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

/**
 * Implementação do serviço de arquivos.
 */
@Service
@RequiredArgsConstructor
public class FileService implements FileServiceInt {

    /**
     * Repositório de arquivos.
     */
    @NonNull
    private FileRepository fileRepository;

    /**
     * Repositório de tarefas.
     */
    @NonNull
    private TaskRepository taskRepository;

    /**
     * Chave de acesso da AWS.
     */
    @Value("${aws.access.key}")
    private String awsKey;

    /**
     * Chave secreta de acesso da AWS.
     */
    @Value("${aws.access.secrety.key}")
    private String awsSecretyKey;

    /**
     * Nome do bucket da AWS.
     */
    @Value("${aws.bucket.name}")
    private String awsBucketName;

    /**
     * Adiciona um arquivo ao sistema.
     *
     * @param id   ID da tarefa associada ao arquivo.
     * @param file Arquivo a ser adicionado.
     * @return Verdadeiro se o arquivo foi adicionado com sucesso, falso caso contrário.
     */
    public Boolean adicionarFile(Long id, MultipartFile file) {

        try {
            // Cria credenciais da AWS
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKey, awsSecretyKey);

            // Cria cliente da AWS S3
            AmazonS3 client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1).build();

            // Verifica se o bucket existe
            boolean bucketExist = client.doesBucketExistV2(awsBucketName);
            if (!bucketExist) {
                return false;
            }

            // Cria tarefa associada ao arquivo
            Task tarefa = new Task();
            tarefa.setId(id);

            // Cria arquivo
            File fileSec = new File();
            fileSec.setTask(tarefa);
            fileSec.setDate(new Date());
            String uuid = UUID.randomUUID().toString();
            fileSec.setReference(uuid);

            // Salva arquivo no repositório
            fileRepository.save(fileSec);

            // Cria metadados do arquivo
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            // Envia arquivo para o bucket da AWS
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

    /**
     * Busca um arquivo no repositório de arquivos e retorna uma URL assinada para acesso ao arquivo no AWS S3.
     *
     * @param id ID do arquivo a ser buscado.
     * @return URL assinada para acesso ao arquivo no AWS S3.
     * @throws RuntimeException se o arquivo não for encontrado.
     */
    public String buscarFilePorID(Long id) {
        /**
         * Busca o arquivo no repositório de arquivos com base no ID.
         */
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File não encontrado"));

        /**
         * Credenciais de acesso ao AWS S3.
         */
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKey, awsSecretyKey);

        /**
         * Cliente do AWS S3 configurado com as credenciais e região.
         */
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_1).build();

        /**
         * Requisição para gerar uma URL assinada para acesso ao arquivo.
         */
        GeneratePresignedUrlRequest presigned =
                new GeneratePresignedUrlRequest(awsBucketName, file.getReference());
        presigned.setExpiration(new Date(System.currentTimeMillis() + 600000));

        /**
         * Gera a URL assinada para acesso ao arquivo.
         */
        String url = client.generatePresignedUrl(presigned).toString();

        return url;
    }
}