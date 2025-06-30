import os

from PIL import Image

def merge_agoutis(in1, in2, out):
    print(out)
    img1 = Image.open(in1)
    img2 = Image.open(in2)
    img_out = img1.copy()
    pixels1 = img1.load()
    pixels2 = img2.load()
    pixels_out = img_out.load()
    for x in range(img1.size[0]):
        for y in range(img1.size[1]):
            pixels_out[x, y] = (0,0,0,min(pixels1[x, y][3], pixels2[x, y][3]))
    img_out.save(out)


if __name__ == '__main__':
    src_dir = "in"
    dst_dir = "out"
    for filename1 in os.listdir(src_dir):
        src_name1 = src_dir + '/' + filename1
        for filename2 in os.listdir(src_dir):
            sorted_filenames = [filename1, filename2]
            sorted_filenames.sort()
            if filename2 != filename1 and filename1 == sorted_filenames[0]:
                src_name2 = src_dir + '/' + filename2
                dst_name = dst_dir + '/' + filename1.removesuffix(".png").removesuffix("_light") + "_" + filename2
                merge_agoutis(src_name1, src_name2, dst_name)
