package com.joewandy.bioinfoapp.main;

import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.phylo.ProgessListenerStub;
import org.biojava3.phylo.TreeConstructionAlgorithm;
import org.biojava3.phylo.TreeConstructor;
import org.biojava3.phylo.TreeType;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TestFastaReader {

    private static final String BASE_PATH = "data\\";
    private static final String FASTA_ALU_FILE = "alu_data.fasta";
    private static final String FASTA_SRA_FILE = "sra_data.fasta";

    public static void main(String[] args) {

        // using biojava

        String dnaSequence = ">gnl|alu|HSU14574 ***ALU WARNING: Human Alu-Sx subfamily consensus sequence.\n"
                + "GGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGA\n"
                + "TCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACT\n"
                + "AAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAG\n"
                + "GCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCG\n"
                + "CCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAAAA\n"
                + ">gnl|alu|HSU14573 ***ALU WARNING: Human Alu-Sq subfamily consensus sequence.\n"
                + "GGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGTGGA\n"
                + "TCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACT\n"
                + "AAAAATACAAAAATTAGCCGGGCGTGGTGGCGGGCGCCTGTAATCCCAGCTACTCGGGAG\n"
                + "GCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCG\n"
                + "CCACTGCACTCCAGCCTGGGCAACAAGAGCGAAACTCCGTCTCAAAAAAAA\n"
                + ">gnl|alu|HSU14572 ***ALU WARNING: Human Alu-Sp subfamily consensus sequence.\n"
                + "GGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGA\n"
                + "TCACCTGAGGTCGGGAGTTCGAGACCAGCCTGACCAACATGGAGAAACCCCGTCTCTACT\n"
                + "AAAAATACAAAAATTAGCCGGGCGTGGTGGCGCATGCCTGTAATCCCAGCTACTCGGGAG\n"
                + "GCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCGGTGAGCCGAGATCGCG\n"
                + "CCATTGCACTCCAGCCTGGGCAACAAGAGCGAAACTCCGTCTCAAAAAAAA";

        InputStream stream;
        try {

            // get sequences as byte array input stream
            stream = new ByteArrayInputStream(dnaSequence.getBytes("UTF-8"));

            // or get sequences from file input stream
//            stream = TestFastaReader.class.getResourceAsStream(TestFastaReader.BASE_PATH + TestFastaReader.FASTA_ALU_FILE);


            // create a map of FASTA id and the sequence content
            Map<String, DNASequence> map = FastaReaderHelper.readFastaDNASequence(stream);

            MultipleSequenceAlignment<DNASequence, NucleotideCompound> multipleSequenceAlignment = new MultipleSequenceAlignment<DNASequence, NucleotideCompound>();

            List<DNASequence> list = new ArrayList<DNASequence>();

            for (Entry<String, DNASequence> entry : map.entrySet()) {
                entry.getValue().setAccession(new AccessionID(entry.getKey()));

                list.add(entry.getValue());

                String key = entry.getKey();
                String seq = entry.getValue().getSequenceAsString();
                System.out.println("key: " + key + "\nseq: " + seq);
            }

            // DNA sequences can now be accessed from the map


            Profile<DNASequence, NucleotideCompound> profile = Alignments.getMultipleSequenceAlignment(list);

            Sequence<NucleotideCompound> sequence;
            DNASequence nucleotideCompounds;

            for (int i = 1; i < profile.getSize() + 1; i++) {
                sequence = profile.getAlignedSequence(i);
                nucleotideCompounds = new DNASequence(sequence.getSequenceAsString(), sequence.getCompoundSet());
                nucleotideCompounds.setAccession(sequence.getAccession());
                multipleSequenceAlignment.addAlignedSequence(nucleotideCompounds);
            }

            TreeConstructor<DNASequence, NucleotideCompound> treeConstructor = new TreeConstructor<DNASequence, NucleotideCompound>(
                    multipleSequenceAlignment,
                    TreeType.NJ,
                    TreeConstructionAlgorithm.PID,
                    new ProgessListenerStub()
            );

            treeConstructor.process();

            treeConstructor.outputPhylipDistances("out/matrix2.txt");

            String newick = treeConstructor.getNewickString(true, true);
            System.out.println(newick);

        } catch (UnsupportedEncodingException e) {
            // thrown by getBytes()
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // thrown by FileInputStream constructor
            e.printStackTrace();
        } catch (Exception e) {
            // thrown by readFastaDNASequence()
            e.printStackTrace();
        }


        // own class

//        System.out.println("Reading " + TestFastaReader.FASTA_ALU_FILE);
//        FastaReader reader = new FastaReader(
//                FastaReader.FastaFileContent.DNA_SEQUENCE,
//                TestFastaReader.FASTA_ALU_FILE);
//        List<Sequence> aluData = reader.processFile();
//        for (Sequence s : aluData) {
//            System.out.println(s);
//        }
//        System.out.println("Loaded " + aluData.size() + " sequences.");
//
//        System.out.println("\nReading " + TestFastaReader.FASTA_SRA_FILE);
//        reader = new FastaReader(FastaReader.FastaFileContent.DNA_SEQUENCE,
//                TestFastaReader.BASE_PATH + TestFastaReader.FASTA_SRA_FILE);
//        List<Sequence> sraData = reader.processFile();
//        for (Sequence s : sraData) {
//            // System.out.println(s);
//        }
//        System.out.println("Loaded " + sraData.size() + " sequences.");

    }

}
