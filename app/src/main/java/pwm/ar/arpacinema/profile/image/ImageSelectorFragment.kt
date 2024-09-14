package pwm.ar.arpacinema.profile.image

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentImageSelectorBinding
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.repository.DTO

class ImageSelectorFragment : Fragment() {

    // test
    private val testingData = List(13) {
        ProfileImage(1, "https://picsum.photos/5000/5000?random")
    }

    private var _binding : FragmentImageSelectorBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ImageSelectorFragment()
    }

    private val viewModel: ImageSelectorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSelectorBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val imageList : List<ProfileImage> = listOf()

        setupBar()


        val imageRecyclerView = binding.profileImageRV

        val adapter = ProfileImageAdapter(imageList) { image ->
            Log.d("ImageSelectorFragment", "Selected image: ${image.imageId}")
            lifecycleScope.launch {
                binding.profileImageRV.alpha = 0.5f
                viewModel.updateImage(image)
                binding.profileImageRV.alpha = 1f
            }

        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == DTO.Stat.DEFAULT) {
                return@observe
            }
            if (it == DTO.Stat.SUCCESS) {
                findNavController().popBackStack()
            } else {
                Dialog.showErrorDialog(requireContext())
            }
        }

        imageRecyclerView.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }

        viewModel.imageList.observe(viewLifecycleOwner) {
            try {
                Log.d("ImageSelectorFragment", "Fetched images: ${it?.size}")
                adapter.setDataList(viewModel.imageList.value!!)
            } catch (e: NullPointerException) {
                Log.d("ImageSelectorFragment", "Fetched images: null")
                Dialog.showErrorDialog(requireContext())
                findNavController().popBackStack()
            }
        }
    }

    private fun setupBar() {
        //setup navbar
        val title = binding.include.viewTitle
        title.text = "Immagine profilo"
        val backButton = binding.include.navBack
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}