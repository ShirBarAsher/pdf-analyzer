PDF Structure Analyzer

This project was part of a home assignment. The goal was to extract internal technical information from PDF files and create a JSON file for each one that includes structural data, not just visible content.

I used Java because it's the language I’m most comfortable with. I used Apache PDFBox to read and parse the PDF files, and Gson to write the data into JSON format. The project was built using Maven.

The script processes all PDF files in a folder, and for each file it extracts:

- PDF version (from the header)
- Metadata like title, author, subject, keywords, creator, producer, creation and modification dates
- Page count
- Number of indirect objects

Producer vs. Creator:  
The "creator" field usually refers to the software used to originally create the document content (like Microsoft Word). The "producer" field refers to the software that converted the document into a PDF (like Adobe Acrobat or a PDF printer). They can be different if the document was written in one tool and exported by another.

Indirect objects are the building blocks of a PDF file. They store things like pages, images, fonts, and metadata, and can be reused throughout the file. Each object has a number and can be referenced from other parts of the PDF.

I also added some extra fields as a kind of document fingerprint:

1. unique_fonts_count  
   This could be calculated by checking the fonts used in the PDF resource dictionaries. Different tools or templates use different fonts, so this can help identify where the document came from or if it was changed.

2. has_images  
   This could be determined by checking if the PDF contains image objects (XObjects). It can help distinguish between scanned/image-heavy documents and plain text ones.

3. file_size_bytes  
   This is based on the actual size of the file in bytes. It can be used to compare versions of the same document or detect significant changes.

Right now, only the file size is implemented. The other two fields appear as null in the output.

This was my first time working with PDF internals, so I read a bit about how PDF files are structured. I learned what indirect objects are and how metadata is stored. I used online examples and documentation to understand PDFBox.

I also used ChatGPT to look up some things I didn't know, like explanations for terms or which Java libraries are available. 
Example of the output structure:

{
    "CreationDate": "java.util.GregorianCalendar[time=1417704581000,areFieldsSet=true,areAllFieldsSet=true,lenient=false,zone=java.util.SimpleTimeZone[id=GMT,offset=0,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,startTimeMode=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0,endTimeMode=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2014,MONTH=11,WEEK_OF_YEAR=49,WEEK_OF_MONTH=1,DAY_OF_MONTH=4,DAY_OF_YEAR=338,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=2,HOUR_OF_DAY=14,MINUTE=49,SECOND=41,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0]",
    "Keywords": "",
    "PDFVersion": 1.4,
    "Title": "",
    "fontList": null,
    "Creator": "LaTeX with hyperref package",
    "Subject": "",
    "fileSizeBytes": 459589,
    "ImageCount": null,
    "Producer": "pdfTeX-1.40.14",
    "PageCount": 6,
    "Modification Date": "java.util.GregorianCalendar[time=1417704581000,areFieldsSet=true,areAllFieldsSet=true,lenient=false,zone=java.util.SimpleTimeZone[id=GMT,offset=0,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,startTimeMode=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0,endTimeMode=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2014,MONTH=11,WEEK_OF_YEAR=49,WEEK_OF_MONTH=1,DAY_OF_MONTH=4,DAY_OF_YEAR=338,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=2,HOUR_OF_DAY=14,MINUTE=49,SECOND=41,MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0]",
    "IndirectObjectsCount": 425,
    "Author": ""
}
