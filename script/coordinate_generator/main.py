import os

from PIL import Image

def generate_coordinates(in1, out):
    print(out)
    img1 = Image.open(in1)
    img_out = img1.copy()
    pixels1 = img1.load()
    pixels_out = img_out.load()
    for x in range(img1.size[0]):
        for y in range(img1.size[1]):
            pixels_out[x, y] = (x*2,y*2,64,(pixels1[x, y][3]))
    img_out.save(out)


if __name__ == '__main__':
    src_dir = "in"
    dst_dir = "out"
    for filename1 in os.listdir(src_dir):
        src_name1 = src_dir + '/' + filename1
        dst_name = dst_dir + '/' + filename1
        generate_coordinates(src_name1, dst_name)
