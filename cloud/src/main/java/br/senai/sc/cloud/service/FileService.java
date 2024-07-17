package br.senai.sc.cloud.service;

import br.senai.sc.cloud.controller.dto.file.FileRequestPostDto;
import br.senai.sc.cloud.model.File;
import br.senai.sc.cloud.model.Task;
import br.senai.sc.cloud.repository.FileRepository;
import br.senai.sc.cloud.repository.TaskRepository;
import br.senai.sc.cloud.service.interfaces.FileServiceInt;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.AwsRegionProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements FileServiceInt {

    @NonNull
    private FileRepository fileRepository;

    @NonNull
    private TaskRepository taskRepository;

    @Value("${aws.access.key}")
    private String awsKey;

    @Value("${aws.access.secrety.key}")
    private String awsSecretyKey;

    @Value("${aws.bucket.name}")
    private String awsBucketName;

    public Boolean adicionarFile(FileRequestPostDto filePostDto) {


        try {
            Task task =  new Task();
            task.setId(filePostDto.tarefaID());

            List<File> files = new ArrayList<>(); // Lista todos os arquivos que ira salvar no banco
            Date data = new Date();

            // Passa por cada multiparteFile e cria um arquivo que salva no banco.
            filePostDto.files().forEach(multipartFile -> {
                File file = new File();
                UUID uuid = UUID.randomUUID(); //Gera um UUID aleatório
                file.setDate(data);
                file.setTask(task);

                // referencia o uuid
                file.setReference(uuid.toString());
                files.add(file);
            });

            // Salva toda a lista no  banco de dados
            fileRepository.saveAll(files);

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKey, awsSecretyKey);

            AmazonS3 client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1).build();

            boolean existBucket = client.doesBucketExistV2(awsBucketName);

            if (!existBucket){
                return false;
            }

        } catch (AmazonS3Exception e){
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }

        return true;
    }

}
